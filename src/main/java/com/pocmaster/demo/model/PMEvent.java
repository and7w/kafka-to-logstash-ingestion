package com.pocmaster.demo.model;

import lombok.Builder;

@Builder
public record PMEvent(
        String key
) {
}
