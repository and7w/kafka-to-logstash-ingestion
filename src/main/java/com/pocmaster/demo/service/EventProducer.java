package com.pocmaster.demo.service;

import com.pocmaster.demo.model.PMEvent;
import com.pocmaster.demo.utils.PMEventGenerator;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Getter
@Component
public class EventProducer  {

    public static final String TOPIC = "eventProducer-out-0";
    private final StreamBridge streamBridge;

    public EventProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }


    @Scheduled(fixedDelayString = "${app.task.schedule.fixed-delay}")
    public void sendMessage(){
        PMEvent pmEvent = PMEventGenerator.generateRandomEvent();
        streamBridge.send(TOPIC, pmEvent);
        log.info("sending new poc master event with uuid {}", pmEvent.key());
    }
}
