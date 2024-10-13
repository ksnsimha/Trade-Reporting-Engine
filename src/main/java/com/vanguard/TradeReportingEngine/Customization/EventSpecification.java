package com.vanguard.TradeReportingEngine.Customization;

import com.vanguard.TradeReportingEngine.Entities.EventEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EventSpecification {

    public static Specification<EventEntity> buildTransactionSpecification(
            String sellerParty1, String currency1,
            String sellerParty2, String currency2,
            List<CustomCondition> additionalConditions) {

        return (root, query, criteriaBuilder) -> {
            // List to store all predicates (conditions)
            List<Predicate> predicates = new ArrayList<>();

            // Condition: (sellerParty = EMU_BANK AND currency = AUD) OR (sellerParty = BISON_BANK AND currency = USD)
            Predicate sellerCurrencyCondition = criteriaBuilder.or(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("sellerParty"), sellerParty1),
                            criteriaBuilder.equal(root.get("premiumCurrency"), currency1)
                    ),
                    criteriaBuilder.and(
                            criteriaBuilder.equal(root.get("sellerParty"), sellerParty2),
                            criteriaBuilder.equal(root.get("premiumCurrency"), currency2)
                    )
            );
            predicates.add(sellerCurrencyCondition);

            // Dynamically add more conditions if needed
            if (additionalConditions != null) {
                for (CustomCondition condition : additionalConditions) {
                    // Add custom condition based on field and value
                    predicates.add(condition.toPredicate(root, criteriaBuilder));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

