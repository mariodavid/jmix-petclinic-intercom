package io.jmix.petclinic.intercom.canvaskit.api.ui.presentation;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.ui.Align;
import io.jmix.petclinic.intercom.canvaskit.api.ui.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ui.ComponentType;
import io.jmix.petclinic.intercom.canvaskit.api.ui.Style;
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
