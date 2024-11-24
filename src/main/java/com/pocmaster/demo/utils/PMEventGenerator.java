package com.pocmaster.demo.utils;

import com.github.javafaker.Faker;
import com.pocmaster.demo.model.PMEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class PMEventGenerator {
    private static final Faker faker = new Faker();

    public static PMEvent generateRandomEvent() {
        return PMEvent.builder()
                .key(UUID.randomUUID().toString())
                .eventName(faker.book().title())
                .eventLocation(faker.address().fullAddress())
                .eventDate(LocalDateTime.now())
                .eventDescription(faker.lorem().sentence())
                .attendeesCount((long) faker.number().numberBetween(10, 200))
                .build();
    }
}
