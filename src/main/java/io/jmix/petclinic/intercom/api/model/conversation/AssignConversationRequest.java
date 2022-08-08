package io.jmix.petclinic.intercom.api.model.conversation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssignConversationRequest {

    @Builder.Default
    String messageType = "assignment";
    @Builder.Default
    String type = "team";
    @Builder.Default
    IntercomAdminOrTeam adminId = IntercomAdminOrTeam.MARIO; // mario.david@jmix-petclinic.intercom-mail.com

    IntercomAdminOrTeam assigneeId;
    String body;
}