package io.jmix.petclinic.intercom.canvaskit.api.ui.presentation;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.ui.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ui.ComponentType;
import lombok.Builder;
import lombok.Data;

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
