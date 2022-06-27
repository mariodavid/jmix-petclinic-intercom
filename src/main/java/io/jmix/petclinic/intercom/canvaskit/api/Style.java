package io.jmix.petclinic.intercom.canvaskit.api;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Style {
    HEADER("header"),
    PARAGRAPH("paragraph"),
    MUTED("muted"),
    ERROR("error");

    private final String id;

    @JsonValue
    public String getId() {
        return id;
    }
}