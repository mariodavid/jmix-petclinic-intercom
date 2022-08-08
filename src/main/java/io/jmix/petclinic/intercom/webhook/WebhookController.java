package io.jmix.petclinic.intercom.webhook;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jmix.petclinic.intercom.webhook.handler.NoopWebhookHandler;
import io.jmix.petclinic.intercom.webhook.handler.WebhookHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class WebhookController {

    private final List<WebhookHandler> webhookHandlers;
    private final ObjectMapper objectMapper;

    @PostMapping("/webhooks")
    @ResponseBody
    public ResponseEntity<String> receiveIntercomWebhook(@RequestBody WebhookData data){


        log.info("Received webhook: {}", data.getTopic());
        log.info(data.toString());



        log.info("Finding Webhook Handler");
        WebhookHandler webhookHandler = webhookHandlers.stream()
                .filter(handler -> handler.supports(data))
                .findFirst()
                .orElse(new NoopWebhookHandler());

        log.info("Found Webhook Handler: {}", webhookHandler.getClass().getSimpleName());

        webhookHandler.handle(webhookHandler.convert(objectMapper, data.getData().getItem()));

        return ResponseEntity.ok("Received");
    }

}