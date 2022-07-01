package io.jmix.petclinic.app.visit;

import io.jmix.core.DataManager;
import io.jmix.core.TimeSource;
import io.jmix.petclinic.app.PetRepository;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitTreatmentStatus;
import io.jmix.petclinic.entity.visit.VisitType;
import io.jmix.petclinic.intercom.canvaskit.book_visit.model.BookVisitRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Slf4j
@Component("petclinic_VisitBookingService")
@RequiredArgsConstructor
public class VisitBookingService {

    private final DataManager dataManager;
    private final PetRepository petRepository;
    private final TimeSource timeSource;

    public Visit bookRegularCheckup(BookVisitRequest bookVisitRequest) {

        Visit visit = dataManager.create(Visit.class);

        visit.setType(VisitType.REGULAR_CHECKUP);
        LocalDateTime visitStart = timeSource.now().toLocalDateTime().plusDays(new Random().nextInt(10) + 1);
        visit.setVisitStart(visitStart);
        visit.setVisitStart(visitStart.plusMinutes(30));
        visit.setTreatmentStatus(VisitTreatmentStatus.UPCOMING);

        visit.setDescription(bookVisitRequest.getVisitDescription());

        Pet pet = petRepository.petsOfOwner(bookVisitRequest.getOwnerEmail())
                .stream()
                .filter(it -> it.getName().equals(bookVisitRequest.getPet()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Pet with name %s was not found", bookVisitRequest.getPet())
                ));

        visit.setPet(pet);

        log.info("Visit will be booked: {}", visit);

        Visit bookedVisit = dataManager.save(visit);

        log.info("Visit booked successfully: {}", visit);

        return bookedVisit;
    }
}