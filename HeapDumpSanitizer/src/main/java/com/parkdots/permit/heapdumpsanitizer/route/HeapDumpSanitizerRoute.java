package com.parkdots.permit.heapdumpsanitizer.route;

import com.parkdots.permit.heapdumpsanitizer.service.HeapDumpToolProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HeapDumpSanitizerRoute extends RouteBuilder {

    @Value("${sanitizer.input-dir:/heap/input}")
    private String inputDir;

    private final HeapDumpToolProcessor heapDumpToolProcessor;

    public HeapDumpSanitizerRoute(HeapDumpToolProcessor heapDumpToolProcessor) {
        this.heapDumpToolProcessor = heapDumpToolProcessor;
    }

    @Override
    public void configure() {

        from("file:" + inputDir +
                "?include=.*.hprof" +
                "&noop=true" +
                "&delete=true" +
                "&initialDelay=2000" +
                "&delay=5000")
                .routeId("heap-dump-sanitizer-route")
                .log("Detected heap dump: ${file:name}")
                .process(heapDumpToolProcessor)
                .log("Heap dump sanitized and stored at: ${header.SanitizedFilePath}");
    }
}
