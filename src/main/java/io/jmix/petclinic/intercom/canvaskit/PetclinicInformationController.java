package io.jmix.petclinic.intercom.canvaskit;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import io.jmix.core.Messages;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.petclinic.entity.User;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitTreatmentStatus;
import io.jmix.petclinic.intercom.api.InitializeRequest;
import io.jmix.petclinic.intercom.canvaskit.api.*;
import io.jmix.petclinic.intercom.canvaskit.api.action.UrlAction;
import io.jmix.petclinic.intercom.canvaskit.api.interactive.ListComponent;
import io.jmix.petclinic.intercom.canvaskit.api.presentation.Divider;
import io.jmix.petclinic.intercom.canvaskit.api.presentation.Text;
import io.jmix.ui.navigation.CrockfordUuidEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/intercom/petclinic-information")
public class PetclinicInformationController {


    @Autowired
    private DataManager dataManager;

    @Autowired
    private SystemAuthenticator systemAuthenticator;
    @Autowired
    private Messages messages;


    @PostMapping("/initialize")
    public ResponseEntity<CanvasResponse> initialize(
            @RequestBody InitializeRequest initializeRequest
    ) {

        UUID userId = initializeRequest.getContact().getExternalId();

        User currentNurse = systemAuthenticator.withSystem(() -> dataManager.load(Id.of(userId, User.class)).one());


        List<Visit> allVisits = systemAuthenticator.withSystem(() ->
                dataManager.load(Visit.class)
                .query("select v from petclinic_Visit v where v.assignedNurse = :currentNurse and v.treatmentStatus in :possibleTreatmentStatus order by v.visitStart")
                .parameter("currentNurse", currentNurse)
                .parameter("possibleTreatmentStatus", List.of(VisitTreatmentStatus.UPCOMING, VisitTreatmentStatus.IN_PROGRESS))
                .fetchPlan(fetchPlanBuilder -> {
                    fetchPlanBuilder.addFetchPlan(FetchPlan.BASE);
                    fetchPlanBuilder.add("assignedNurse", FetchPlan.BASE);
                    fetchPlanBuilder.add("pet", FetchPlan.BASE);
                })
                .list()
                        .subList(0, 4)
        );

        toListItems(allVisits);

        List<Component> components = List.of(
                Text.builder()
                        .id("text")
                        .style(Style.HEADER)
                        .text("Upcoming Visits")
                        .build(),
                Divider.builder().build(),
                ListComponent.builder()
                        .items(toListItems(allVisits))
                        .build()
        );
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CanvasResponse.withComponents(components));

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
                .action(UrlAction.builder().url(String.format("http://localhost:8080/#main/visits/edit?id=%s", CrockfordUuidEncoder.encode(visit.getId()))).build())
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