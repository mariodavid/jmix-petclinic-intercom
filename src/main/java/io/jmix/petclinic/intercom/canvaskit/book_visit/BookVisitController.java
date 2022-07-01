package io.jmix.petclinic.intercom.canvaskit.book_visit;

import io.jmix.core.Messages;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.petclinic.app.PetRepository;
import io.jmix.petclinic.app.VisitRepository;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.intercom.canvaskit.book_visit.model.PetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/intercom/book-visit")
public class BookVisitController {
    @Autowired
    private SystemAuthenticator systemAuthenticator;
    @Autowired
    private PetRepository petRepository;


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

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(petsForOwner);
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