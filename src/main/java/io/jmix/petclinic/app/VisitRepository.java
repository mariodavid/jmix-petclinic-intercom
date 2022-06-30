package io.jmix.petclinic.app;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.Id;
import io.jmix.petclinic.entity.User;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.entity.visit.VisitTreatmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("petclinic_VisitRepository")
public class VisitRepository {

    @Autowired
    private DataManager dataManager;

    public List<Visit> activeVisitsForUser(UUID userId) {
        User currentNurse = dataManager.load(Id.of(userId, User.class)).one();

        return dataManager.load(Visit.class)
                .query("select v from petclinic_Visit v where v.assignedNurse = :currentNurse and v.treatmentStatus in :possibleTreatmentStatus order by v.visitStart")
                .parameter("currentNurse", currentNurse)
                .parameter("possibleTreatmentStatus", List.of(VisitTreatmentStatus.UPCOMING, VisitTreatmentStatus.IN_PROGRESS))
                .fetchPlan(fetchPlanBuilder -> {
                    fetchPlanBuilder.addFetchPlan(FetchPlan.BASE);
                    fetchPlanBuilder.add("assignedNurse", FetchPlan.BASE);
                    fetchPlanBuilder.add("pet", FetchPlan.BASE);
                })
                .list();
    }
}