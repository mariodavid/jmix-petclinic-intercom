package io.jmix.petclinic.intercom.api.model.conversation;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum IntercomAdminOrTeam {

    // Teams
    DOCTORS("5607103"),


    // Admins
    MARIO("5587573");

    private final String id;

    @JsonValue
    public String getId() {
        return id;
    }
}
