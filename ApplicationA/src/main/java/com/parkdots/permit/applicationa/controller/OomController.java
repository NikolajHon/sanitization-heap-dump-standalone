package com.parkdots.permit.applicationa.controller;

import com.parkdots.permit.applicationa.model.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class OomController {

    private static final Logger log = LoggerFactory.getLogger(OomController.class);

    private final List<UserSession> sessions = new ArrayList<>();

    @GetMapping("/api/oom")
    public String triggerOom() {
        log.info("Starting OOM loop in Application A");
        while (true) {
            sessions.add(new UserSession(
                    UUID.randomUUID().toString(),
                    new byte[1024 * 1024]   // 1 MB payload
            ));
        }
    }
}
