package io.jmix.petclinic.intercom.canvaskit.api.ui.presentation;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.ui.*;
import io.jmix.petclinic.intercom.canvaskit.api.ui.action.UrlAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Image implements Component {
    String id;
    String url;
    ImageAlign align;
    int width;
    int height;
    boolean rounded;
    String bottomMargin;
    UrlAction action;

    @Override
    public ComponentType getType() {
        return ComponentType.IMAGE;
    }


    @RequiredArgsConstructor
    public enum ImageAlign {
        LEFT("left"),
        CENTER("center"),
        RIGHT("right"),
        FULL_WIDTH("full_width");

        private final String id;

        @JsonValue
        public String getId() {
            return id;
        }
    }
}
