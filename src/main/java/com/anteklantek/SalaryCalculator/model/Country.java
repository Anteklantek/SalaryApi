package com.anteklantek.SalaryCalculator.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Data
@JsonPropertyOrder(value = {"name", "fixedCost", "tax", "currencyCode"})
public class Country {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(unique = true)
    private String name;

    @NotNull
    private BigDecimal fixedCost;

    @NotNull
    private Integer tax;

    @NotNull
    private String currencyCode;

}
