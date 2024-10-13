package com.vanguard.TradeReportingEngine.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;

/**
 *
 */
@Entity
@Getter
@Setter
@Table(name = "events")
public class EventEntity {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID
    private Long id;

    @Column(name = "buyer_party", nullable = false) // Map to column 'buyer_party'
    private String buyerParty;

    @Column(name = "seller_party", nullable = false) // Map to column 'seller_party'
    private String sellerParty;

    @Column(name = "premium_amount", nullable = false, precision = 19, scale = 4) // Map to 'premium_amount' and set precision
    private BigDecimal premiumAmount;

    @Column(name = "premium_currency", nullable = false) // Map to 'premium_currency'
    private String premiumCurrency;


}