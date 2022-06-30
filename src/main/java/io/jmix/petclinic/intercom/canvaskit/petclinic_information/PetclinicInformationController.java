package io.jmix.petclinic.intercom.canvaskit.petclinic_information;

import io.jmix.core.Messages;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.petclinic.app.VisitRepository;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.intercom.api.InitializeRequest;
import io.jmix.petclinic.intercom.canvaskit.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/intercom/petclinic-information")
public class PetclinicInformationController {
    @Autowired
    private SystemAuthenticator systemAuthenticator;
    @Autowired
    private Messages messages;
    @Autowired
    private VisitRepository visitRepository;


    @PostMapping("/initialize")
    public ResponseEntity<CanvasResponse> initialize(
            @RequestBody InitializeRequest initializeRequest
    ) {

        List<Visit> latestActiveVisitsForUser = systemAuthenticator.withSystem(() ->
                visitRepository.activeVisitsForUser(initializeRequest.getContact().getExternalId())
                        .subList(0, 4)
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                    CanvasResponse.fromView(
                            new UpcomingVisitsView(messages, latestActiveVisitsForUser)
                    )
                );

    }
}