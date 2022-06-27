package io.jmix.petclinic.screen.visit.cancellation;

import io.jmix.ui.screen.*;
import io.jmix.petclinic.entity.visit.VisitCancellation;

@UiController("petclinic_VisitCancellation.browse")
@UiDescriptor("visit-cancellation-browse.xml")
@LookupComponent("visitCancellationsTable")
public class VisitCancellationBrowse extends StandardLookup<VisitCancellation> {
}