package io.jmix.petclinic.intercom.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactRequest {

    ContactRole role;
    String externalId;
    String phone;
    String email;
    String name;


    @RequiredArgsConstructor
    public enum ContactRole {
        USER("user"),
        LEAD("lead");

        private final String id;

        @JsonValue
        public String getId() {
            return id;
        }
    }


}
