package io.jmix.petclinic.intercom.canvaskit.api.interactive;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.Action;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DropdownComponent implements Component {
    String id;
    String label;
    List<Option> options;
    String value;
    SaveState saveState;
    boolean disabled;
    Action action;

    @Override
    public ComponentType getType() {
        return ComponentType.DROPDOWN;
    }

}
