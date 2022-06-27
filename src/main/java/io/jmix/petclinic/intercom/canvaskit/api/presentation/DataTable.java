package io.jmix.petclinic.intercom.canvaskit.api.presentation;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.jmix.petclinic.intercom.canvaskit.api.Component;
import io.jmix.petclinic.intercom.canvaskit.api.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DataTable implements Component {
    String id;


    List<DataTableItem> items;

    @Override
    public ComponentType getType() {
        return ComponentType.DATA_TABLE;
    }


    @Data
    @Builder
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DataTableItem {
        String field;
        String value;

        public DataTableItemType getType() {
            return DataTableItemType.FIELD_VALUE;
        }
        @RequiredArgsConstructor
        public enum DataTableItemType {
            FIELD_VALUE("field-value");

            private final String id;

            @JsonValue
            public String getId() {
                return id;
            }
        }
    }

}
