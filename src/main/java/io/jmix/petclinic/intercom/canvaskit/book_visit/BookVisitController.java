package io.jmix.petclinic.intercom.canvaskit.book_visit;

import io.jmix.core.Messages;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.petclinic.app.PetRepository;
import io.jmix.petclinic.app.visit.VisitBookingService;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.intercom.canvaskit.book_visit.model.BookVisitRequest;
import io.jmix.petclinic.intercom.canvaskit.book_visit.model.PetResponse;
import io.jmix.petclinic.intercom.canvaskit.book_visit.model.VisitResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/intercom/book-visit")
@RequiredArgsConstructor
public class BookVisitController {
    private final SystemAuthenticator systemAuthenticator;
    private final PetRepository petRepository;
    private final VisitBookingService visitBookingService;
    private final Messages messages;


    @GetMapping("/pets")
    public ResponseEntity<List<PetResponse>> loadPetsForOwner(
            @RequestParam String ownerEmail
    ) {

        if (!StringUtils.hasText(ownerEmail)) {
            log.error("No Owner Email Address provided");

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(List.of());
        }

        List<PetResponse> petsForOwner = systemAuthenticator.withSystem(() ->
                petRepository.petsOfOwner(ownerEmail)
        )
                .stream()
                .map(this::toPetResponse)
                .collect(Collectors.toList());

        log.info("Return following Pets: {}", petsForOwner);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(petsForOwner);
    }

    @PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VisitResponse> bookVisit(
            @RequestBody BookVisitRequest data
    ) {

        log.info("Retrieved Visit Booking Request: {}", data);

        Visit bookedVisit = systemAuthenticator.withSystem(() ->
            visitBookingService.bookRegularCheckup(data)
        );

        VisitResponse response = VisitResponse.builder()
                .id(bookedVisit.getId())
                .type(messages.getMessage(bookedVisit.getType()))
                .visitStart(formatDate(bookedVisit))
                .build();


        log.info("Return Visit Response: {}", response);

        return ResponseEntity
            .status(HttpStatus.CREATED)
                .body(response);
    }

    private String formatDate(Visit bookedVisit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM HH:mm");
        return bookedVisit.getVisitStart().format(formatter);
    }

    private PetResponse toPetResponse(Pet pet) {
        return PetResponse.builder()
                .id(pet.getId())
                .name(pet.getName())
                .identificationNumber(pet.getIdentificationNumber())
                .type(pet.getType().getName())
                .build();
    }
}