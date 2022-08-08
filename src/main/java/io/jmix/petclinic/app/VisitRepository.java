package io.jmix.petclinic.app;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import io.jmix.petclinic.entity.User;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitTreatmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component("petclinic_VisitRepository")
public class VisitRepository {

    @Autowired
    private DataManager dataManager;

    public List<Visit> activeVisitsForUser(UUID userId) {
        Optional<User> potentialCurrentNurse = dataManager.load(Id.of(userId, User.class)).optional();

        if (potentialCurrentNurse.isEmpty()) {
            return Collections.emptyList();
        }

        return dataManager.load(Visit.class)
                .query("select v from petclinic_Visit v where v.assignedNurse = :currentNurse and v.treatmentStatus in :possibleTreatmentStatus order by v.visitStart")
                .parameter("currentNurse", potentialCurrentNurse.get())
                .parameter("possibleTreatmentStatus", List.of(VisitTreatmentStatus.UPCOMING, VisitTreatmentStatus.IN_PROGRESS))
                .fetchPlan(fetchPlanBuilder -> {
                    fetchPlanBuilder.addFetchPlan(FetchPlan.BASE);
                    fetchPlanBuilder.add("assignedNurse", FetchPlan.BASE);
                    fetchPlanBuilder.add("pet", FetchPlan.BASE);
                })
                .list();

    }

    public List<Visit> latestActiveVisitsForUser(UUID userId) {
        List<Visit> visits = activeVisitsForUser(userId);

        if (visits.size() > 5) {
            return visits.subList(0, 4);
        }

        return visits;
    }
}