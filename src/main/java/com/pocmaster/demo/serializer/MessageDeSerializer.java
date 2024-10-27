package com.pocmaster.demo.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pocmaster.demo.model.PMEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class MessageDeSerializer implements Deserializer<PMEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public PMEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), PMEvent.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
