package io.jmix.petclinic.intercom.notification.event;

import io.jmix.core.Messages;
import io.jmix.core.MetadataTools;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;

import java.time.Instant;
import java.util.Map;

public interface IntercomEvent {

    String getEventName();
    Map<String, Object> getMetadata(Messages messages, DatatypeFormatter datatypeFormatter);

    default long getCreatedAt() {
        return Instant.now().getEpochSecond();
    }


}
