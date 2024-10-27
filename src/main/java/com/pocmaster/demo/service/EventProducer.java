package com.pocmaster.demo.service;

import com.pocmaster.demo.model.PMEvent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Log4j2
@Getter
@Component
public class EventProducer  {

    public static final String TOPIC = "eventProducer-out-0";

    private final StreamBridge streamBridge;

    public EventProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }


    @Scheduled(cron = "*/2 * * * * *")
    public void sendMessage(){
        PMEvent pmEvent = PMEvent.builder().key(UUID.randomUUID().toString()).build();
        streamBridge.send(TOPIC, pmEvent);
        log.info("sending new poc master envent with uuid {}", pmEvent.key());
    }


}
