package io.jmix.petclinic.intercom.api;

import io.jmix.petclinic.intercom.api.model.ContactRequest;
import io.jmix.petclinic.intercom.api.model.SearchByEmailRequest;
import io.jmix.petclinic.intercom.api.model.SearchByExternalIdRequest;
import io.jmix.petclinic.intercom.api.model.SearchContactsResponse;
import io.jmix.petclinic.intercom.api.model.conversation.AssignConversationRequest;
import io.jmix.petclinic.intercom.api.model.conversation.UpdateConversationRequest;
import io.jmix.petclinic.intercom.sync.IntercomConversationRoutingConfig;
import io.jmix.petclinic.intercom.sync.IntercomSyncConfig;
import io.jmix.petclinic.intercom.webhook.Conversation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class IntercomAPI {

    private final IntercomSyncConfig intercomSyncConfig;
    private final IntercomConversationRoutingConfig intercomConversationRoutingConfig;

    public List<Contact> findByUserId(UUID userId) {
        ResponseEntity<SearchContactsResponse> response = webClient()
                .post()
                .uri("/contacts/search")
                .bodyValue(SearchByExternalIdRequest.create(userId))
                .retrieve()
                .toEntity(SearchContactsResponse.class)
                .block();

        log.info("Received data from /contacts/search: {}", response.getBody());

        return response.getBody().getData();
    }

    public List<Contact> findByEmail(String email) {
        ResponseEntity<SearchContactsResponse> response = webClient()
                .post()
                .uri("/contacts/search")
                .bodyValue(SearchByEmailRequest.create(email))
                .retrieve()
                .toEntity(SearchContactsResponse.class)
                .block();

        log.info("Received data from /contacts/search: {}", response.getBody());

        return response.getBody().getData();
    }

    public Contact createContact(ContactRequest request) {
        log.info("Creating contact: {}", request);


        ResponseEntity<Contact> response = webClient()
                .post()
                .uri("/contacts")
                .bodyValue(request)
                .retrieve()
                .toEntity(Contact.class)
                    .block();

        log.info("Contact created: {}", response.getBody());

        return response.getBody();
    }

    public Contact updateContact(String userId, ContactRequest request) {

        log.info("Updating contact: {}", request);

        ResponseEntity<Contact> response = webClient()
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/contacts/{contactId}")
                        .build(userId))
                .bodyValue(request)
                .retrieve()
                .toEntity(Contact.class)
                .block();

        log.info("Contact updated: {}", response.getBody());

        return response.getBody();
    }


    public void markConversationAsPriority(String conversationId) {

        UpdateConversationRequest request = UpdateConversationRequest.builder()
                .customAttributes(Map.of("custom-priority", true))
                .build();

        log.info("Marking conversation as priority via custom attribute 'custom-priority': {}", request);

        ResponseEntity<Conversation> response = conversationRoutingWebClient()
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path("/conversations/{conversationId}")
                        .build(conversationId))
                .bodyValue(request)
                .retrieve()
                .toEntity(Conversation.class)
                .block();
    }

    private WebClient webClient() {
        return WebClient.builder()
                .baseUrl(intercomSyncConfig.getBaseUrl())
                .defaultHeaders(header -> header.setBearerAuth(intercomSyncConfig.getAccessToken()))
                .build();
    }
    private WebClient conversationRoutingWebClient() {
        return WebClient.builder()
                .baseUrl(intercomConversationRoutingConfig.getBaseUrl())
                .defaultHeaders(header -> header.setBearerAuth(intercomConversationRoutingConfig.getAccessToken()))
                .build();
    }

    public void assignConversation(String conversationId, AssignConversationRequest request) {

        log.info("Assigning Conversation: {}", request);

        ResponseEntity<Conversation> response = conversationRoutingWebClient()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/conversations/{conversationId}/parts")
                        .build(conversationId))
                .bodyValue(request)
                .retrieve()
                .toEntity(Conversation.class)
                .block();

        if (!response.getStatusCode().is2xxSuccessful()) {
            log.error("Conversation Assignment was not successful. Error: {}", response);
        }
    }
}
