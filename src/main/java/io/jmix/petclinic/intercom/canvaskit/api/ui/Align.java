package io.jmix.petclinic.intercom.canvaskit.api.ui;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Align {
    LEFT("left"),
    CENTER("center"),
    RIGHT("right");

    private final String id;

    @JsonValue
    public String getId() {
        return id;
    }
}