package io.jmix.petclinic.intercom.sync;

import io.intercom.api.Intercom;
import io.jmix.core.DataManager;
import io.jmix.petclinic.entity.owner.Owner;
import io.jmix.petclinic.intercom.api.Contact;
import io.jmix.petclinic.intercom.api.IntercomAPI;
import io.jmix.petclinic.intercom.api.model.ContactRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Component
public class IntercomOwnerSync {

    private final DataManager dataManager;
    private final IntercomSyncConfig intercomSyncConfig;
    private final IntercomAPI intercomAPI;

    public boolean sync() {

        Intercom.setToken(intercomSyncConfig.getAccessToken());

        List<Owner> allOwners = dataManager.load(Owner.class)
                .all()
                .list();

        allOwners
                .forEach(owner ->  {
                    List<io.jmix.petclinic.intercom.api.Contact> matchingContactsByExternalId = intercomAPI.findByUserId(owner.getId());
                    List<io.jmix.petclinic.intercom.api.Contact> matchingContactsByEmail = intercomAPI.findByEmail(owner.getEmail());

                    if (CollectionUtils.isEmpty(matchingContactsByExternalId) && CollectionUtils.isEmpty(matchingContactsByEmail)) {

                        intercomAPI.createContact(
                                ContactRequest.builder()
                                        .role(ContactRequest.ContactRole.USER)
                                        .externalId(owner.getId().toString())
                                        .name(owner.getName())
                                        .email(owner.getEmail())
                                        .phone(owner.getTelephone())
                                        .build()
                        );
                    }
                    else {
                        Contact existingContact = Stream.concat(
                                        matchingContactsByEmail.stream(),
                                        matchingContactsByExternalId.stream()
                                ).findFirst()
                                .orElseThrow();


                        String userId = existingContact.getId();

                        String externalId = existingContact.getExternalId() == null ? null : owner.getId().toString();

                        intercomAPI.updateContact(
                                userId,
                                ContactRequest.builder()
                                        .role(ContactRequest.ContactRole.USER)
                                        .externalId(externalId)
                                        .name(owner.getName())
                                        .email(owner.getEmail())
                                        .phone(owner.getTelephone())
                                        .build()
                        );
                    }
                });

        return true;
    }

}