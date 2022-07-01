package io.jmix.petclinic.intercom.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.intercom.api.Admin;
import io.intercom.api.Conversation;
import io.jmix.petclinic.entity.CancellationCostCoverage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CancelVisitSubmitRequest {
    String workspaceId;
    Admin admin;
    Conversation conversation;
    Customer customer;
    Contact contact;

    InputValues inputValues;
    String componentId;

   public boolean doneClicked() {
        return inputValues == null && componentId != null && componentId.equals("done");
    }


    @Data
    @Builder
    @AllArgsConstructor
    public static class InputValues {
        String reason;
        String visitId;
        CancellationCostCoverage costCoverage;
        List<String> ownerInformed;

        public Boolean ownerInformedChecked() {
            return !CollectionUtils.isEmpty(ownerInformed) && ownerInformed.contains("ownerInformed");
        }
    }
}