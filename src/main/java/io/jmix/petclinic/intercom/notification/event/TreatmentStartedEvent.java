package io.jmix.petclinic.intercom.notification.event;

import io.jmix.core.Messages;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.ui.navigation.CrockfordUuidEncoder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZoneOffset;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class TreatmentStartedEvent implements IntercomEvent {
    Visit visit;
    @Override
    public String getEventName() {
        return "treatment-started";
    }

    @Override
    public Map<String, Object> getMetadata(Messages messages, DatatypeFormatter datatypeFormatter) {
        return Map.of(
                "Visit Start", datatypeFormatter.formatLocalDateTime(visit.getVisitStart()),
                "Visit Type", messages.getMessage(visit.getType()),
                "Pet Name", visit.getPetName()
        );
    }
}
