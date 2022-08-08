package io.jmix.petclinic.intercom.webhook.handler;

import io.jmix.core.DataManager;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.SystemAuthenticator;
import io.jmix.petclinic.app.EmployeeRepository;
import io.jmix.petclinic.entity.User;
import io.jmix.petclinic.entity.owner.Owner;
import io.jmix.petclinic.intercom.api.IntercomAPI;
import io.jmix.petclinic.intercom.api.model.conversation.AssignConversationRequest;
import io.jmix.petclinic.intercom.api.model.conversation.IntercomAdminOrTeam;
import io.jmix.petclinic.intercom.webhook.Conversation;
import io.jmix.petclinic.intercom.webhook.Topic;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConversationStartedHandler extends AbstractWebhookHandler<Conversation> {

    private final EmployeeRepository employeeRepository;
    private final IntercomAPI intercomAPI;

    private final SystemAuthenticator systemAuthenticator;
    private final DataManager dataManager;

    @Override
    protected Topic supportedTopic() {
        return Topic.CONVERSATION_USER_CREATED;
    }

    protected Class<Conversation> getDataClass() {
        return Conversation.class;
    }

    @SneakyThrows
    @Override
    public void handle(Conversation conversation) {
        log.info("Try to route conversation to the correct team in 30 seconds... {}", conversation);

        String emailAddress = conversation.getSource().getAuthor().getEmail();

        log.info("Searching for Nurse with Email: {}", emailAddress);
        Optional<User> potentialNurse = systemAuthenticator.withSystem(() ->
                employeeRepository
                .findAllNurses()
                .stream()
                .filter(nurse -> Objects.equals(nurse.getEmail(),emailAddress))
                .findFirst()
        );

        if (potentialNurse.isPresent()) {
            log.info("Nurse found with Email {}. Assign to Doctors Team.", emailAddress);
            intercomAPI.assignConversation(
                    conversation.getId(),
                    AssignConversationRequest.builder()
                        .assigneeId(IntercomAdminOrTeam.DOCTORS)
                        .body("Assigned to Team 'Doctors' by Conversation Routing, since contact is a nurse.")
                        .build()
            );
        }
        else {

            log.info("No nurse found with Email {}", emailAddress);

            log.info("Searching for Owner with Email: {}", emailAddress);
            if (isOwner(emailAddress)) {
                log.info("Owner found with Email {}. Marking conversation as priority.", emailAddress);
                intercomAPI.markConversationAsPriority(conversation.getId());
            }
            else {
                log.info("No Nurse / Owner found with Email {}. Do nothing", emailAddress);
            }
        }
    }

    private boolean isOwner(String emailAddress) {
        List<Owner> ownersWithEmail = systemAuthenticator.withSystem(() ->
                dataManager.load(Owner.class)
                        .condition(PropertyCondition.equal("email", emailAddress))
                        .list()
        );

        log.info("Following Owners found with Email '{}': {}", emailAddress, ownersWithEmail);

        return !ownersWithEmail.isEmpty();
    }

}
