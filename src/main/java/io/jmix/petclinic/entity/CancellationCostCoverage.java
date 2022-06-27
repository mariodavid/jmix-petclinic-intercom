package io.jmix.petclinic.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum CancellationCostCoverage implements EnumClass<String> {

    OWNER("OWNER"),
    PETCLINIC("PETCLINIC"),
    HALF_HALF_SPLIT("HALF_HALF_SPLIT");

    private String id;

    CancellationCostCoverage(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CancellationCostCoverage fromId(String id) {
        for (CancellationCostCoverage at : CancellationCostCoverage.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}