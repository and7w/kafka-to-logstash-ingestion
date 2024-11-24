package com.pocmaster.demo.service;

import com.pocmaster.demo.model.PMEvent;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Log4j2
@Getter
@Component
public class EventConsumer implements Consumer<PMEvent> {

    @Override
    public void accept(PMEvent pmEvent) {
        log.debug("Consumer consume Kafka message -> {}", pmEvent);
    }
}