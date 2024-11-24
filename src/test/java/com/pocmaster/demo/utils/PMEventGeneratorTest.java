package com.pocmaster.demo.utils;

import com.pocmaster.demo.model.PMEvent;import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.UUID;
import java.time.LocalDateTime;

class PMEventGeneratorTest {

    @Test
    void testGenerateRandomEvent() {
        // Act
        PMEvent event = PMEventGenerator.generateRandomEvent();

        // Assert - check that event is not null
        assertNotNull(event, "Generated event should not be null");

        // Assert - check that fields are populated and meet expected conditions
        assertNotNull(event.key(), "Event key should not be null");
        assertDoesNotThrow(() -> UUID.fromString(event.key()), "Event key should be a valid UUID");

        assertNotNull(event.eventName(), "Event name should not be null");
        assertFalse(event.eventName().isBlank(), "Event name should not be blank");

        assertNotNull(event.eventLocation(), "Event location should not be null");
        assertFalse(event.eventLocation().isBlank(), "Event location should not be blank");

        assertNotNull(event.eventDate(), "Event date should not be null");
        assertTrue(event.eventDate().isBefore(LocalDateTime.now().plusSeconds(1)), "Event date should be close to the current time");

        assertNotNull(event.eventDescription(), "Event description should not be null");
        assertFalse(event.eventDescription().isBlank(), "Event description should not be blank");

        assertNotNull(event.attendeesCount(), "Attendees count should not be null");
        assertTrue(event.attendeesCount() >= 10 && event.attendeesCount() <= 200, "Attendees count should be between 10 and 200");
    }
}

