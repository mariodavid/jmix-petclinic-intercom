package io.jmix.petclinic.intercom.notification.event;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EventMetadata {

    @Data
    @Builder
    @AllArgsConstructor
    public static class MonetaryAmount {
        BigDecimal amount;
        String currency;

        public Map<String, Object> toMap() {
            return Map.of(
                    "amount", amount,
                    "currency", currency
            );
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static class RichLink {
        String url;
        String value;

        public Map<String, Object> toMap() {
            return Map.of(
                    "url", url,
                    "value", value
            );
        }
    }
}
