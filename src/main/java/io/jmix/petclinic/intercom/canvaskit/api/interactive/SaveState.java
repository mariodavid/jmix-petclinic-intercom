package io.jmix.petclinic.intercom.canvaskit.api.interactive;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SaveState {
    UNSAVED("unsaved"),
    SAVED("saved"),
    FAILED("failed");

    private final String id;

    @JsonValue
    public String getId() {
        return id;
    }
}