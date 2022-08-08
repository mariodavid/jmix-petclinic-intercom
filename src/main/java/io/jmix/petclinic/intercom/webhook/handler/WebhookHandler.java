package io.jmix.petclinic.intercom.webhook.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.petclinic.intercom.webhook.WebhookData;
import org.springframework.scheduling.annotation.Async;

public interface WebhookHandler<T> {

    boolean supports(WebhookData webhookData);

    T convert(ObjectMapper objectMapper, JsonNode data);

    @Async
    void handle(T data);
}
