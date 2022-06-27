package io.jmix.petclinic.intercom.canvaskit.api.interactive;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.Action;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TextAreaComponent implements Component {
    String id;
    String label;
    String placeholder;
    String value;
    boolean error;
    boolean disabled;

    @Override
    public ComponentType getType() {
        return ComponentType.TEXTAREA;
    }
}
