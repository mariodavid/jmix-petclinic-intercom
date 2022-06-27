package io.jmix.petclinic.intercom.canvaskit.api.action;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.Action;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SheetAction implements Action {

    String url;

    @Override
    public ActionType getType() {
        return ActionType.SHEET;
    }

}
