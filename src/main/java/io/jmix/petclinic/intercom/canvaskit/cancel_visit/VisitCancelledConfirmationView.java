package io.jmix.petclinic.intercom.canvaskit.cancel_visit;

import io.jmix.petclinic.intercom.canvaskit.api.Canvas;
import io.jmix.petclinic.intercom.canvaskit.api.ui.Component;
import io.jmix.petclinic.intercom.canvaskit.api.Content;
import io.jmix.petclinic.intercom.canvaskit.api.ui.action.SubmitAction;
import io.jmix.petclinic.intercom.canvaskit.api.ui.interactive.ButtonComponent;
import io.jmix.petclinic.intercom.canvaskit.api.ui.presentation.Divider;
import io.jmix.petclinic.intercom.canvaskit.api.ui.presentation.Text;
import io.jmix.petclinic.intercom.canvaskit.api.CanvaskitView;
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
