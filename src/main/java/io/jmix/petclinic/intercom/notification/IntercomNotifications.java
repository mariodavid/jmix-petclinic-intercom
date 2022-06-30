package io.jmix.petclinic.intercom.notification;

import io.intercom.api.Event;
import io.intercom.api.Intercom;
import io.jmix.core.Messages;
import io.jmix.core.metamodel.datatype.DatatypeFormatter;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.petclinic.entity.User;
import io.jmix.petclinic.intercom.notification.event.IntercomEvent;
import io.jmix.petclinic.intercom.sync.IntercomSyncConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component("petclinic_IntercomNotifications")
public class IntercomNotifications {
    private final IntercomSyncConfig intercomSyncConfig;
    private final CurrentAuthentication currentAuthentication;
    private final Messages messages;
    private final DatatypeFormatter datatypeFormatter;

    public <T extends IntercomEvent> void notify(T event) {

        Intercom.setToken(intercomSyncConfig.getAccessToken());

        Event intercomEvent = new Event()
                .setEventName(event.getEventName())
                .setUserID(currentUserId())
                .setCreatedAt(event.getCreatedAt())
                .setMetadata(event.getMetadata(messages, datatypeFormatter));

        Event.create(intercomEvent);
    }

    private String currentUserId() {
        return ((User) currentAuthentication.getUser()).getId().toString();
    }
}