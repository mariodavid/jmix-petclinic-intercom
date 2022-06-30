package io.jmix.petclinic.intercom.canvaskit.cancel_visit;

import io.jmix.petclinic.intercom.canvaskit.api.Canvas;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.Content;
import io.jmix.petclinic.intercom.canvaskit.api.action.SubmitAction;
import io.jmix.petclinic.intercom.canvaskit.api.interactive.ButtonComponent;
import io.jmix.petclinic.intercom.canvaskit.api.presentation.Divider;
import io.jmix.petclinic.intercom.canvaskit.api.presentation.Text;
import io.jmix.petclinic.intercom.canvaskit.CanvaskitView;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class VisitCancelledConfirmationView implements CanvaskitView {
    @Override
    public Canvas render() {

        List<Component> components = List.of(
                Text.builder().text("The visit was cancelled successfully")
                        .build(),
                Divider.builder().build(),
                ButtonComponent.builder()
                        .label("Done")
                        .id("done")
                        .buttonStyle(ButtonComponent.ButtonStyle.SECONDARY)
                        .action(SubmitAction.builder().build())
                        .build()

        );

        return Canvas.builder()
                .content(Content.builder()
                        .components(components)
                        .build())
                .build();
    }

}
