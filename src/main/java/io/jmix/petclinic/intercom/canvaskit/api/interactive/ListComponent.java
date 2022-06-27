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
public class ListComponent implements Component {
    String id;
    List<ListItem> items;

    @Override
    public ComponentType getType() {
        return ComponentType.LIST;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ListItem {
        String id;
        String title;
        String subtitle;
        String tertiaryText;
        String image;
        int imageWidth;
        int imageHeight;
        boolean disabled;
        Action action;

        public String getType() {
            return "item";
        }

    }

}
