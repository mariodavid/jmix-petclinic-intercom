package io.jmix.petclinic.screen.visit.cancellation;

import io.jmix.core.SaveContext;
import io.jmix.petclinic.app.visit.VisitCancellationService;
import io.jmix.ui.screen.*;
import io.jmix.petclinic.entity.visit.VisitCancellation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@UiController("petclinic_VisitCancellation.edit")
@UiDescriptor("visit-cancellation-edit.xml")
@EditedEntityContainer("visitCancellationDc")
public class VisitCancellationEdit extends StandardEditor<VisitCancellation> {

    @Autowired
    private VisitCancellationService visitCancellationService;

    @Install(target = Target.DATA_CONTEXT)
    private Set<Object> commitDelegate(SaveContext saveContext) {
        VisitCancellation visitCancellation = visitCancellationService.cancelVisit(getEditedEntity());
        return Set.of(visitCancellation);
    }


}