package com.vanguard.TradeReportingEngine.Customization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vanguard.TradeReportingEngine.Entities.EventEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomCondition {
    @JsonProperty
    private String fieldName;
    @JsonProperty
    private Object value;
    @JsonProperty
    private final ComparisonType comparisonType;

    public CustomCondition(String fieldName, Object value, ComparisonType comparisonType) {
        this.fieldName = fieldName;
        this.value = value;
        this.comparisonType = comparisonType;
    }

    public Predicate toPredicate(Root<EventEntity> root, CriteriaBuilder criteriaBuilder) {
        switch (comparisonType) {
            case EQUALS:
                return criteriaBuilder.equal(root.get(fieldName), value);
            case NOT_EQUALS:
                return criteriaBuilder.notEqual(root.get(fieldName), value);
            default:
                throw new IllegalArgumentException("Unsupported comparison type");
        }
    }
}

