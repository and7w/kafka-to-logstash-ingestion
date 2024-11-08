package com.pocmaster.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.task.schedule")
public record ScheduleConfigProperties(String fixedDelay) {
}
