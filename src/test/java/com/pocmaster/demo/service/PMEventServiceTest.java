package com.pocmaster.demo.service;

import com.pocmaster.demo.model.PMEvent;import com.pocmaster.demo.utils.PMEventGenerator;import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;

import java.time.LocalDateTime;import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PMEventServiceTest {

    private static final String TOPIC = "your-topic-name"; // Replace with actual topic name

    @Mock
    private StreamBridge streamBridge;

    @InjectMocks
    private EventProducer eventProducer;

    @Test
    void testSendMessage() {
        // Arrange
        PMEvent pmEvent = PMEvent.builder()
                .key("test-key")
                .eventName("Test Event")
                .eventLocation("Test Location")
                .eventDate(LocalDateTime.now())
                .eventDescription("Test Description")
                .attendeesCount(100L)
                .build();

        // Mock la méthode statique pour générer un événement
        try (MockedStatic<PMEventGenerator> mocked = mockStatic(PMEventGenerator.class)) {
            mocked.when(PMEventGenerator::generateRandomEvent).thenReturn(pmEvent);

            // Act
            eventProducer.sendMessage();

            // Assert
            verify(streamBridge).send("pm-events", pmEvent);
        }
    }
}

