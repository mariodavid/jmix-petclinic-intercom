package io.jmix.petclinic.intercom.canvaskit.cancel_visit;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.petclinic.app.VisitRepository;
import io.jmix.petclinic.app.visit.VisitCancellationService;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitCancellation;
import io.jmix.petclinic.intercom.api.CancelVisitSubmitRequest;
import io.jmix.petclinic.intercom.api.Contact;
import io.jmix.petclinic.intercom.api.InitializeRequest;
import io.jmix.petclinic.intercom.canvaskit.api.CanvasResponse;
import io.jmix.petclinic.intercom.canvaskit.cancel_visit.CancelVisitFormView;
import io.jmix.petclinic.intercom.canvaskit.cancel_visit.VisitCancelledConfirmationView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/intercom/cancel-visit")
public class CancelVisitController {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private VisitRepository visitRepository;
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
        return cancelVisitForm(initializeRequest.getContact());
    }

    @PostMapping("/submit")
    public ResponseEntity<CanvasResponse> submit(
            @RequestBody CancelVisitSubmitRequest data
        ) {

        if (data.doneClicked()) {
            log.info("Visit Cancellation Form done clicked");
            return cancelVisitForm(data.getContact());
        }

        log.info("Received Submit to cancel a visit: {}", data);

        VisitCancellation cancelledVisit = cancelVisit(data);

        log.info("Visit cancelled via Intercom: {}", cancelledVisit);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(CanvasResponse.fromView(new VisitCancelledConfirmationView()));

    }

    private ResponseEntity<CanvasResponse> cancelVisitForm(Contact contact) {
        List<Visit> visitsToSelect = latestActiveVisitsForUser(contact);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        CanvasResponse.fromView(
                                new CancelVisitFormView(messages, metadataTools, visitsToSelect)
                        )
                );
    }

    private List<Visit> latestActiveVisitsForUser(Contact contact) {
        return systemAuthenticator.withSystem(() ->
                visitRepository.activeVisitsForUser(contact.getExternalId())
                        .subList(0, 4)
        );
    }

    private VisitCancellation cancelVisit(CancelVisitSubmitRequest data) {
        return systemAuthenticator.withSystem(() -> {
            VisitCancellation visitCancellation = dataManager.create(VisitCancellation.class);
            visitCancellation.setVisit(
                    dataManager.load(Id.of(UUID.fromString(data.getInputValues().getVisitId()), Visit.class)).one()
            );
            visitCancellation.setReason(data.getInputValues().getReason());
            visitCancellation.setCostCoverage(data.getInputValues().getCostCoverage());
            visitCancellation.setOwnerInformed(data.getInputValues().ownerInformedChecked());
            return visitCancellationService.cancelVisit(visitCancellation);
        });
    }
}