package com.parkdots.permit.heapdumpsanitizer.route;

import com.parkdots.permit.heapdumpsanitizer.config.AppProperties;
import com.parkdots.permit.heapdumpsanitizer.service.HeapDumpToolProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class HeapDumpSanitizerRoute extends RouteBuilder {

    private final AppProperties appProperties;
    private final HeapDumpToolProcessor heapDumpToolProcessor;

    public HeapDumpSanitizerRoute(AppProperties appProperties,
                                  HeapDumpToolProcessor heapDumpToolProcessor) {
        this.appProperties = appProperties;
        this.heapDumpToolProcessor = heapDumpToolProcessor;
    }

    @Override
    public void configure() {

        from("file:" + appProperties.getInputDir()
                + "?include=.*\\.hprof"
                + "&initialDelay=2000"
                + "&delay=5000"
                + "&noop=false"
                + "&readLock=changed"
                + "&readLockMinAge=15000"
                + "&readLockCheckInterval=3000"
        )
                .routeId("heap-dump-sanitizer-route")
                .log("Detected heap dump: ${file:name}")
                .process(heapDumpToolProcessor)
                .log("Heap dump sanitized and stored at: ${header.SanitizedFilePath}");
    }

}
