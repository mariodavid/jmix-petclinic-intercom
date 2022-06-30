package io.jmix.petclinic.intercom.canvaskit.petclinic_information;

import io.jmix.core.Messages;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitTreatmentStatus;
import io.jmix.petclinic.intercom.canvaskit.api.Canvas;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.Content;
import io.jmix.petclinic.intercom.canvaskit.api.Style;
import io.jmix.petclinic.intercom.canvaskit.api.action.UrlAction;
import io.jmix.petclinic.intercom.canvaskit.api.interactive.ListComponent;
import io.jmix.petclinic.intercom.canvaskit.api.presentation.Divider;
import io.jmix.petclinic.intercom.canvaskit.api.presentation.Text;
import io.jmix.petclinic.intercom.canvaskit.CanvaskitView;
import io.jmix.ui.navigation.CrockfordUuidEncoder;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UpcomingVisitsView implements CanvaskitView {
    private final Messages messages;
    private final List<Visit> visits;

    @Override
    public Canvas render() {

        List<Component> components = List.of(
                Text.builder()
                        .id("text")
                        .style(Style.HEADER)
                        .text("Upcoming Visits")
                        .build(),
                Divider.builder().build(),
                ListComponent.builder()
                        .items(toListItems(visits))
                        .build()
        );

        return Canvas.builder()
                .content(Content.builder()
                        .components(components)
                        .build())
                .build();
    }


    private List<ListComponent.ListItem> toListItems(List<Visit> visits) {
        return visits.stream()
                .map(this::toListItem)
                .collect(Collectors.toList());

    }

    private ListComponent.ListItem toListItem(Visit visit) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM HH:mm");

        String formatDateTime = visit.getVisitStart().format(formatter);

        return ListComponent.ListItem.builder()
                .id(visit.getId().toString())
                .subtitle(messages.getMessage(visit.getType()))
                .title(visit.getPetName())
                .tertiaryText(formatDateTime)
                .image(imageForStatus(visit.getTreatmentStatus()))
                .imageWidth(32)
                .imageHeight(32)
                .action(UrlAction.builder().url(String.format("https://jmix-petclinic-intercom.herokuapp.com/#main/visits/edit?id=%s", CrockfordUuidEncoder.encode(visit.getId()))).build())
                .build();
    }

    private String imageForStatus(VisitTreatmentStatus treatmentStatus) {
        return switch (treatmentStatus) {
            case IN_PROGRESS -> "https://cdn1.iconfinder.com/data/icons/essentials-pack/96/checkmark_complete_done_yes_correct-128.png";
            case UPCOMING -> "https://cdn1.iconfinder.com/data/icons/essentials-pack/96/clock_time_alarm_watch_hour-128.png";
            default -> null;
        };
    }
}
