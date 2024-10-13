package com.vanguard.TradeReportingEngine.Customization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vanguard.TradeReportingEngine.Entities.EventEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomCondition {
    @JsonProperty
    @Pattern(
            regexp = "buyer_party|seller_party|premium_amount|premium_currency",
            message = "Invalid fieldName. Allowed values are buyer_party, seller_party, premium_amount, and premium_currency"
    )
    private String fieldName;
    @JsonProperty
    private Object value;
    @JsonProperty
    @Pattern(regexp = "EQUALS|NOT_EQUALS",
            message = "Invalid comparisonType. Allowed values are EQUALS and NOT_EQUALS")
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

