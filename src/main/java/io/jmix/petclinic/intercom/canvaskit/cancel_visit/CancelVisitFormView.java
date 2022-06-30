package io.jmix.petclinic.intercom.canvaskit.cancel_visit;

import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.petclinic.entity.CancellationCostCoverage;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.petclinic.intercom.canvaskit.api.Canvas;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.Content;
import io.jmix.petclinic.intercom.canvaskit.api.action.SubmitAction;
import io.jmix.petclinic.intercom.canvaskit.api.interactive.*;
import io.jmix.petclinic.intercom.canvaskit.api.presentation.Divider;
import io.jmix.petclinic.intercom.canvaskit.CanvaskitView;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CancelVisitFormView implements CanvaskitView {
    private final Messages messages;
    private final MetadataTools metadataTools;
    private final List<Visit> visits;

    @Override
    public Canvas render() {

        return Canvas.builder()
                .content(Content.builder()
                        .components(cancelVisitForm(visits))
                        .build())
                .build();
    }


    private List<Component> cancelVisitForm(List<Visit> visits) {
        return List.of(
                DropdownComponent.builder()
                        .label("Select Visit")
                        .id("visitId")
                        .options(toOptions(visits))
                        .build(),
                Divider.builder().build(),
                TextAreaComponent.builder()
                        .label("Reason")
                        .id("reason")
                        .placeholder("Reason for Cancellation...")
                        .build(),
                DropdownComponent.builder()
                        .label("Cost Coverage")
                        .id("costCoverage")
                        .options(cancellationCoverageOptions())
                        .build(),
                CheckboxComponent.builder()
                        .label("Communication")
                        .id("ownerInformed")
                        .options(List.of(Option.builder()
                                .id("ownerInformed")
                                .text("Owner informed")
                                .build()
                        ))
                        .build(),
                ButtonComponent.builder()
                        .label("Cancel Visit")
                        .id("cancelVisit")
                        .buttonStyle(ButtonComponent.ButtonStyle.PRIMARY)
                        .action(SubmitAction.builder().build())
                        .build()

        );
    }

    private List<Option> cancellationCoverageOptions() {
        return Arrays.stream(CancellationCostCoverage.values())
                .map(cancellationCostCoverage -> Option.builder()
                        .text(messages.getMessage(cancellationCostCoverage))
                        .id(cancellationCostCoverage.getId())
                        .build()
                )
                .collect(Collectors.toList());
    }


    private List<Option> toOptions(List<Visit> visits) {
        return visits.stream()
                .map(this::toOption)
                .collect(Collectors.toList());

    }
    private Option toOption(Visit visit) {
        return Option.builder()
                .id(visit.getId().toString())
                .text(metadataTools.getInstanceName(visit))
                .build();
    }
}
