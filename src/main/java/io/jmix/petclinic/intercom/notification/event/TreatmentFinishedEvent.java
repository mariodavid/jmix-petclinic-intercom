package io.jmix.petclinic.intercom.notification.event;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.core.Messages;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.petclinic.entity.pet.Pet;
import io.jmix.petclinic.entity.visit.Visit;
import io.jmix.ui.navigation.CrockfordUuidEncoder;
import jdk.jfr.Event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Delegate;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class TreatmentFinishedEvent implements IntercomEvent {
    Visit visit;
    @Override
    public String getEventName() {
        return "treatment-finished";
    }

    @Override
    public Map<String, Object> getMetadata(Messages messages, DatatypeFormatter datatypeFormatter) {
        return Map.of(
                "Visit End", datatypeFormatter.formatLocalDateTime(visit.getVisitEnd()),
                "Visit Type", messages.getMessage(visit.getType()),
                "Pet Name", visit.getPetName()
        );
    }
}
