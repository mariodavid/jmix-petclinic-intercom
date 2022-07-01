package io.jmix.petclinic.intercom.canvaskit.api.ui.interactive;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.ui.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ui.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CheckboxComponent implements Component {
    String id;
    String label;
    List<Option> options;
    String value;
    SaveState saveState;
    boolean disabled;

    @Override
    public ComponentType getType() {
        return ComponentType.CHECKBOX;
    }

}
