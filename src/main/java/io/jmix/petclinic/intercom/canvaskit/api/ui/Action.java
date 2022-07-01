package io.jmix.petclinic.intercom.canvaskit.api.ui;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.RequiredArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public interface Action {

    ActionType getType();

    @RequiredArgsConstructor
    public enum ActionType {
        SUBMIT("submit"),
        URL("url"),
        SHEET("sheet");
        private final String id;

        @JsonValue
        public String getId() {
            return id;
        }
    }
}
