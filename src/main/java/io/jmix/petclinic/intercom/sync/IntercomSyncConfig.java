package io.jmix.petclinic.intercom.sync;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "intercom.sync")
public class IntercomSyncConfig {

    @NotBlank
    private String accessToken;
    @NotBlank
    private String baseUrl;

}