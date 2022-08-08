package io.jmix.petclinic.intercom.webhook.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.petclinic.intercom.webhook.Topic;
import io.jmix.petclinic.intercom.webhook.WebhookApiModel;
import io.jmix.petclinic.intercom.webhook.WebhookData;
import lombok.SneakyThrows;

public abstract class AbstractWebhookHandler<T extends WebhookApiModel> implements WebhookHandler<T> {
    @Override
    public boolean supports(WebhookData webhookData) {
        return webhookData.getTopic().equals(supportedTopic());
    }

    protected abstract Topic supportedTopic();

    protected abstract Class<T> getDataClass();

    @SneakyThrows
    @Override
    public T convert(ObjectMapper objectMapper, JsonNode data) {
        return objectMapper.readValue(objectMapper.treeAsTokens(data), getDataClass());
    }


}
