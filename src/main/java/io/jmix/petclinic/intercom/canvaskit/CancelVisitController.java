package io.jmix.petclinic.intercom.canvaskit;

import io.jmix.core.*;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.petclinic.app.visit.VisitCancellationService;
import io.jmix.petclinic.entity.CancellationCostCoverage;
import io.jmix.petclinic.entity.User;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitCancellation;
import io.jmix.petclinic.entity.visit.VisitTreatmentStatus;
import io.jmix.petclinic.intercom.api.CancelVisitSubmitRequest;
import io.jmix.petclinic.intercom.api.Contact;
import io.jmix.petclinic.intercom.api.InitializeRequest;
import io.jmix.petclinic.intercom.canvaskit.api.*;
import io.jmix.petclinic.intercom.canvaskit.api.action.SubmitAction;
import io.jmix.petclinic.intercom.canvaskit.api.interactive.*;
import io.jmix.petclinic.intercom.canvaskit.api.presentation.Divider;
import io.jmix.petclinic.intercom.canvaskit.api.presentation.Text;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/intercom/cancel-visit")
public class CancelVisitController {


    @Autowired
    private DataManager dataManager;


    @Autowired
    private VisitCancellationService visitCancellationService;

    @Autowired
    private SystemAuthenticator systemAuthenticator;
    @Autowired
    private Messages messages;
    @Autowired
    private MetadataTools metadataTools;


    @PostMapping("/initialize")
    public ResponseEntity<CanvasResponse> initialize(
            @RequestBody InitializeRequest initializeRequest
    ) {
        List<Visit> allVisits = allVisitForNurse(initializeRequest.getContact());

        return canvas(cancelVisitForm(allVisits));

    }

    private List<Visit> allVisitForNurse(Contact contact) {
        UUID userId = contact.getExternalId();

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
        return allVisits;
    }

    private ResponseEntity<CanvasResponse> canvas(List<Component> components) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CanvasResponse.withComponents(components));
    }

    private List<Component> cancelVisitForm(List<Visit> allVisits) {
        return List.of(
                DropdownComponent.builder()
                        .label("Select Visit")
                        .id("visitId")
                        .options(toOptions(allVisits))
                        .build(),
                Divider.builder().build(),
                TextAreaComponent.builder()
                        .label("Reason")
                        .id("reason")
                        .placeholder("Reason for Cancellation...")
                        .build(),
                DropdownComponent.builder()
                        .label("Cost Coverage")
                        .id("costCoverage")
                        .options(cancellationCoverageOptions())
                        .build(),
                CheckboxComponent.builder()
                        .label("Owner informed")
                        .id("ownerInformed")
                        .options(List.of(Option.builder()
                                .id("ownerInformed")
                                .text("Owner informed")
                                .build()
                        ))
                        .build(),
                ButtonComponent.builder()
                        .label("Cancel Visit")
                        .id("cancelVisit")
                        .buttonStyle(ButtonComponent.ButtonStyle.PRIMARY)
                        .action(SubmitAction.builder().build())
                        .build()

        );
    }

    @PostMapping("/submit")
    public ResponseEntity<CanvasResponse> submit(
            @RequestBody CancelVisitSubmitRequest data
        ) {

        if (data.doneClicked()) {
            log.info("Visit Cancellation Form done clicked");
            return canvas(cancelVisitForm(allVisitForNurse(data.getContact())));
        }



        log.info("Received Submit to cancel a visit: {}", data);

        VisitCancellation cancelledVisit = systemAuthenticator.withSystem(() -> {
            VisitCancellation visitCancellation = dataManager.create(VisitCancellation.class);
            visitCancellation.setVisit(
                    dataManager.load(Id.of(UUID.fromString(data.getInputValues().getVisitId()), Visit.class)).one()
            );
            visitCancellation.setReason(data.getInputValues().getReason());
            visitCancellation.setCostCoverage(data.getInputValues().getCostCoverage());
            visitCancellation.setOwnerInformed(data.getInputValues().ownerInformedChecked());
            return visitCancellationService.cancelVisit(visitCancellation);
        });
        log.info("Visit cancelled via Intercom: {}", cancelledVisit);


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

        return canvas(components);

    }

    private List<Option> cancellationCoverageOptions() {
        return Arrays.stream(CancellationCostCoverage.values())
                .map(cancellationCostCoverage -> Option.builder()
                        .text(messages.getMessage(cancellationCostCoverage))
                        .id(cancellationCostCoverage.getId())
                        .build()
                )
                .collect(Collectors.toList());
    }


    private List<Option> toOptions(List<Visit> visits) {
        return visits.stream()
                    .map(this::toOption)
                    .collect(Collectors.toList());

    }
    private Option toOption(Visit visit) {
        return Option.builder()
                .id(visit.getId().toString())
                .text(metadataTools.getInstanceName(visit))
                .build();
    }
}