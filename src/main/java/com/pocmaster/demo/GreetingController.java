package com.pocmaster.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/whoami")
    public String getMessage() {
        return "POCMaster: -  Logstash Ingestion with kafka and Log4j2";
    }
}
