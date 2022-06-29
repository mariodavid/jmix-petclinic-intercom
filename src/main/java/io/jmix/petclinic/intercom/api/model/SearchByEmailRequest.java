package io.jmix.petclinic.intercom.api.model;

import io.jmix.petclinic.intercom.api.Query;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchByEmailRequest {
    Query query;


    public static SearchByEmailRequest create(String email) {
        return SearchByEmailRequest.builder()
                .query(
                        Query.builder()
                                .field("email")
                                .operator(Query.Operator.EQUALS)
                                .value(email)
                        .build()
                )
                .build();
    }
}
