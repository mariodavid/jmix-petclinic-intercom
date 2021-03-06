package io.jmix.petclinic.intercom.api;

import io.jmix.petclinic.intercom.api.model.ContactRequest;
import io.jmix.petclinic.intercom.api.model.SearchByEmailRequest;
import io.jmix.petclinic.intercom.api.model.SearchByExternalIdRequest;
import io.jmix.petclinic.intercom.api.model.SearchContactsResponse;
import io.jmix.petclinic.intercom.sync.IntercomSyncConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class IntercomAPI {

    private final IntercomSyncConfig intercomSyncConfig;

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

    private WebClient webClient() {
        return WebClient.builder()
                .baseUrl(intercomSyncConfig.getBaseUrl())
                .defaultHeaders(header -> header.setBearerAuth(intercomSyncConfig.getAccessToken()))
                .build();
    }

}
