package io.jmix.petclinic.intercom.canvaskit.api.presentation;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Spacer implements Component {

    String id;
    SpacerSize size;

    @Override
    public ComponentType getType() {
        return ComponentType.SPACER;
    }

    @RequiredArgsConstructor
    public enum SpacerSize {
        XS("xs"),
        S("s"),
        M("M"),
        L("l"),
        XL("xl");

        private final String id;

        @JsonValue
        public String getId() {
            return id;
        }
    }
}
