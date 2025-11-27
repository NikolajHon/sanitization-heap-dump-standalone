package com.parkdots.permit.applicationb.controller;

import com.parkdots.permit.applicationb.model.ProductOrder;
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

    private final List<ProductOrder> orders = new ArrayList<>();

    @GetMapping("/api/oom")
    public String triggerOom() {
        log.info("Starting OOM loop in Application B");
        while (true) {
            orders.add(new ProductOrder(
                    UUID.randomUUID().toString(),
                    "Demo product",
                    "Mykola",
                    "testmail@gmail.com",
                    "Honcharenko",
                    new byte[1024 * 1024]   // 1 MB payload
            ));
        }
    }
}
