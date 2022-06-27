package io.jmix.petclinic.intercom.canvaskit.api.interactive;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Option {
    String id;
    String text;
    boolean disabled;

    public String getType() {
        return "option";
    }

}
