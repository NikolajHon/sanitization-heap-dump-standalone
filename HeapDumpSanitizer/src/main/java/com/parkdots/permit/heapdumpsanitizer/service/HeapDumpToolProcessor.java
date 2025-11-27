package com.parkdots.permit.heapdumpsanitizer.service;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class HeapDumpToolProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(HeapDumpToolProcessor.class);

    @Value("${sanitizer.output-dir:/heap/output}")
    private String outputDir;

    @Value("${sanitizer.heap-dump-tool-path:/opt/heap-dump-tool/heap-dump-tool.jar}")
    private String toolJarPath;

    @Override
    public void process(Exchange exchange) throws Exception {
        String inputFilePath = exchange.getIn().getHeader("CamelFilePath", String.class);
        if (inputFilePath == null) {
            throw new IllegalStateException("CamelFilePath header is null, cannot sanitize heap dump");
        }

        Path input = Paths.get(inputFilePath);
        String fileName = input.getFileName().toString();

        Path sanitized = Paths.get(outputDir, fileName);

        LOG.info("Starting sanitization of heap dump: {} -> {}", input, sanitized);

        List<String> command = new ArrayList<>();
        command.add("java");
        command.add("-jar");
        command.add(toolJarPath);
        command.add("sanitize");
        command.add(input.toString());
        command.add(sanitized.toString());

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        Process process = pb.start();

        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                LOG.info("[heap-dump-tool] {}", line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IllegalStateException("heap-dump-tool failed with exit code " + exitCode);
        }

        LOG.info("Sanitization completed for: {}", fileName);

        exchange.getMessage().setHeader("SanitizedFilePath", sanitized.toString());
    }
}
