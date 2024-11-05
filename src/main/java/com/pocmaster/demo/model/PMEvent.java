package com.pocmaster.demo.model;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record PMEvent(
        String key,
        String eventName,
        String eventLocation,
        String eventDescription,
        OffsetDateTime eventDate,
        Long attendeesCount
) { }
