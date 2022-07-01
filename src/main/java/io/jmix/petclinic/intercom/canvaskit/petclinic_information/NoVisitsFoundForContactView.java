package io.jmix.petclinic.intercom.canvaskit.petclinic_information;

import io.jmix.petclinic.intercom.api.Contact;
import io.jmix.petclinic.intercom.canvaskit.api.Canvas;
import io.jmix.petclinic.intercom.canvaskit.api.CanvaskitView;
import io.jmix.petclinic.intercom.canvaskit.api.Content;
import io.jmix.petclinic.intercom.canvaskit.api.ui.Style;
import io.jmix.petclinic.intercom.canvaskit.api.ui.presentation.Divider;
import io.jmix.petclinic.intercom.canvaskit.api.ui.presentation.Image;
import io.jmix.petclinic.intercom.canvaskit.api.ui.presentation.Text;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class NoVisitsFoundForContactView implements CanvaskitView {
    private final Contact contact;
    @Override
    public Canvas render() {

        return Canvas.builder()
                .content(Content.builder()
                        .components(List.of(
                                Text.builder()
                                        .id("text")
                                        .style(Style.HEADER)
                                        .text(String.format("No Visits found for Contact {}. Upcoming Visits are only displayed for Nurses.", contact))
                                        .build(),
                                Divider.builder().build(),
                                Image.builder()
                                        .width(128)
                                        .height(128)
                                        .align(Image.ImageAlign.CENTER)
                                        .url("https://cdn4.iconfinder.com/data/icons/emoji-18/61/22-512.png")
                                        .build()
                        ))
                        .build())
                .build();
    }
}
