package io.jmix.petclinic.intercom.canvaskit.api.ui.interactive;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.ui.Action;
import io.jmix.petclinic.intercom.canvaskit.api.ui.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ui.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ButtonComponent implements Component {
    String id;
    String label;
    Action action;
    ButtonStyle buttonStyle;
    boolean disabled;

    @Override
    public ComponentType getType() {
        return ComponentType.BUTTON;
    }


    @RequiredArgsConstructor
    public enum ButtonStyle {
        PRIMARY("primary"),
        SECONDARY("secondary"),
        LINK("link");

        private final String id;

        @JsonValue
        public String getId() {
            return id;
        }
    }

}
