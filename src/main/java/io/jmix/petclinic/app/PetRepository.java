package io.jmix.petclinic.app;

import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.petclinic.entity.owner.Owner;
import io.jmix.petclinic.entity.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component("petclinic_PetRepository")
public class PetRepository {

    @Autowired
    private DataManager dataManager;

    public List<Pet> petsOfOwner(String ownerEmail) {
        List<Owner> owners = dataManager.load(Owner.class)
                .condition(PropertyCondition.equal("email", ownerEmail))
                .fetchPlan(fetchPlanBuilder -> {
                    fetchPlanBuilder.addFetchPlan(FetchPlan.BASE);
                    fetchPlanBuilder.add("pets", FetchPlan.BASE);
                })
                .list();

        if (CollectionUtils.isEmpty(owners)) {
            return List.of();
        }

        return owners.get(0).getPets();
    }
}