package io.jmix.petclinic.intercom.canvaskit.api.presentation;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.Align;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ComponentType;
import io.jmix.petclinic.intercom.canvaskit.api.Style;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Text implements Component {
    String id;
    Align align;
    Style style;
    String text;
    String bottomMargin;



    @Override
    public ComponentType getType() {
        return ComponentType.TEXT;
    }
}
