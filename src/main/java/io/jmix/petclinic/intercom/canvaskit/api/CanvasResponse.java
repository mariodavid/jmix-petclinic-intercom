package io.jmix.petclinic.intercom.canvaskit.api;

import io.jmix.petclinic.intercom.canvaskit.CanvaskitView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanvasResponse {
    Canvas canvas;

    public static CanvasResponse fromView(CanvaskitView canvaskitView) {
        return CanvasResponse.builder()
                .canvas(canvaskitView.render())
                .build();
    }
    public static CanvasResponse withComponents(List<Component> components) {
        return CanvasResponse.builder()
                .canvas(Canvas.builder()
                        .content(Content.builder()
                                .components(components)
                                .build())
                        .build())
                .build();
    }
}