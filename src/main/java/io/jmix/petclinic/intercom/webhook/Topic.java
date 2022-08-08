package io.jmix.petclinic.intercom.webhook;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Topic {

    CONVERSATION_USER_CREATED("conversation.user.created"),
    PING("ping"),

    @JsonEnumDefaultValue
    UNKNOWN(null);

    private final String id;

    @JsonValue
    public String getId() {
        return id;
    }
}
