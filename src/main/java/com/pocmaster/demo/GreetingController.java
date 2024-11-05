package com.pocmaster.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/salutation")
    public String getMessage() {
        return "Thank you...POCMaster man 3";
    }
}
