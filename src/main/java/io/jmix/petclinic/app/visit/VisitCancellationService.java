package io.jmix.petclinic.app.visit;

import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitCancellation;
import io.jmix.petclinic.entity.visit.VisitTreatmentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("petclinic_VisitCancellationService")
public class VisitCancellationService {

    @Autowired
    private DataManager dataManager;

    public VisitCancellation cancelVisit(VisitCancellation visitCancellation) {

        Visit visit = visitCancellation.getVisit();

        log.info("Visit will be cancelled: {}", visit);

        visit.setTreatmentStatus(VisitTreatmentStatus.CANCELLED);

        dataManager.save(visitCancellation, visit);

        log.info("Visit cancelled successfully: {}", visit);

        return dataManager.load(Id.of(visitCancellation)).one();
    }
}