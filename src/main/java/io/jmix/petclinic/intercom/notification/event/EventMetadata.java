package io.jmix.petclinic.intercom.notification.event;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
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
