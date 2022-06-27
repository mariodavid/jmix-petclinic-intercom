package io.jmix.petclinic.intercom.api;

import io.intercom.api.Admin;
import io.intercom.api.Conversation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InitializeRequest {
    String workspaceId;
    Admin admin;
    Conversation conversation;
    Customer customer;
    Contact contact;
}