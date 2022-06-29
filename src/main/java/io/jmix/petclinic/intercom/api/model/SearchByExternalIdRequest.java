package io.jmix.petclinic.intercom.api.model;

import io.jmix.petclinic.intercom.api.Query;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SearchByExternalIdRequest {
    Query query;


    public static SearchByExternalIdRequest create(UUID externalId) {
        return SearchByExternalIdRequest.builder()
                .query(
                        Query.builder()
                                .field("external_id")
                                .operator(Query.Operator.EQUALS)
                                .value(externalId.toString())
                        .build()
                )
                .build();
    }
}
