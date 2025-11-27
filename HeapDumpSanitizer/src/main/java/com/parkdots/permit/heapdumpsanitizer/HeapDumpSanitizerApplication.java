package com.parkdots.permit.heapdumpsanitizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HeapDumpSanitizerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HeapDumpSanitizerApplication.class, args);
    }
}
