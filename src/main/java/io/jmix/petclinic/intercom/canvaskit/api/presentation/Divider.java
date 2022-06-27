package io.jmix.petclinic.intercom.canvaskit.api.presentation;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ComponentType;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Divider implements Component {
    String id;

    @Override
    public ComponentType getType() {
        return ComponentType.DIVIDER;
    }
}
