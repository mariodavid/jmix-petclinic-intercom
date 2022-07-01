package io.jmix.petclinic.intercom.canvaskit.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


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

    public static ResponseEntity<CanvasResponse> create(CanvaskitView canvaskitView) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        fromView(canvaskitView)
                );
    }
}