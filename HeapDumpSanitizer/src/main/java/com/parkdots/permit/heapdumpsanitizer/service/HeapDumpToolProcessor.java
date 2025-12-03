package com.parkdots.permit.heapdumpsanitizer.service;

import com.paypal.heapdumptool.sanitizer.HeapDumpSanitizer;
import com.paypal.heapdumptool.sanitizer.SanitizeCommand;
import com.paypal.heapdumptool.sanitizer.SanitizeStreamFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class HeapDumpToolProcessor implements Processor {

    private static final Logger LOG = LoggerFactory.getLogger(HeapDumpToolProcessor.class);

    @Value("${sanitizer.output-dir:/heap/output}")
    private String outputDir;

    @Override
    public void process(Exchange exchange) throws Exception {
        String inputFile = exchange.getIn().getHeader("CamelFilePath", String.class);
        if (inputFile == null) {
            throw new IllegalStateException("CamelFilePath header missing");
        }

        Path input = Paths.get(inputFile);
        Path output = Paths.get(outputDir, input.getFileName().toString());

        LOG.info("Sanitizing heap dump: {} -> {}", input, output);
        SanitizeCommand command = new SanitizeCommand();
        command.setInputFile(input);
        command.setOutputFile(output);

        SanitizeStreamFactory streamFactory = new SanitizeStreamFactory(command);

        try (InputStream inputStream = streamFactory.newInputStream();
             OutputStream outputStream = streamFactory.newOutputStream()) {
            HeapDumpSanitizer sanitizer = new HeapDumpSanitizer();
            sanitizer.setSanitizeCommand(command);
            sanitizer.setInputStream(inputStream);
            sanitizer.setOutputStream(outputStream);
            sanitizer.setProgressMonitor(bytes -> {});
            sanitizer.sanitize();
        }

        LOG.info("Sanitization completed: {}", output);

        exchange.getMessage().setHeader("SanitizedFilePath", output.toString());
    }
}
