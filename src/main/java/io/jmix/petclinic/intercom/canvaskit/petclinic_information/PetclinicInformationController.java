package io.jmix.petclinic.intercom.canvaskit.petclinic_information;

import io.jmix.core.Id;
import io.jmix.core.Messages;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.petclinic.app.VisitRepository;
import io.jmix.petclinic.entity.User;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.intercom.api.InitializeRequest;
import io.jmix.petclinic.intercom.canvaskit.api.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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
            @RequestBody InitializeRequest data
    ) {

        if (data.getContact().getExternalId() == null) {
            log.warn("No external ID found in Contact");

            return CanvasResponse.create(
                new NoVisitsFoundForContactView(data.getContact())
            );
        }

        List<Visit> latestActiveVisitsForUser = systemAuthenticator.withSystem(() ->
                visitRepository.latestActiveVisitsForUser(data.getContact().getExternalId())
        );

        return CanvasResponse.create(
                new UpcomingVisitsView(messages, latestActiveVisitsForUser)
        );

    }
}