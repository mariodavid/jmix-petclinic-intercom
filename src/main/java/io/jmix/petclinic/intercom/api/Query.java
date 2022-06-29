package io.jmix.petclinic.intercom.api;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class Query {
    String field;
    Operator operator;
    String value;

    @RequiredArgsConstructor
    public enum Operator {
        EQUALS("=");

        private final String id;

        @JsonValue
        public String getId() {
            return id;
        }
    }
}
