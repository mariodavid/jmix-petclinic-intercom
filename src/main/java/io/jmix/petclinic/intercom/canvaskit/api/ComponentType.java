package io.jmix.petclinic.intercom.canvaskit.api;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ComponentType {
    TEXT("text"),
    DATA_TABLE("data-table"),
    IMAGE("image"),
    SPACER("spacer"),
    BUTTON("button"),
    INPUT("input"),
    TEXTAREA("textarea"),
    LIST("list"),
    SINGLE_SELECT("single-select"),
    CHECKBOX("checkbox"),
    DROPDOWN("dropdown"),
    DIVIDER("divider");

    private final String id;

    @JsonValue
    public String getId() {
        return id;
    }
}