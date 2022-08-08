package io.jmix.petclinic.intercom.webhook.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.petclinic.intercom.webhook.WebhookData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoopWebhookHandler implements WebhookHandler<JsonNode> {
    @Override
    public boolean supports(WebhookData webhookData) {
        return true;
    }

    @Override
    public JsonNode convert(ObjectMapper objectMapper, JsonNode data) {
        return data;
    }

    @Override
    public void handle(JsonNode data) {
      log.info(String.format("Webhook event was not handled. Data: %s", data));
    }
}
