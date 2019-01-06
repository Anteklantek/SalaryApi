package com.anteklantek.SalaryCalculator.controller.viewmodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryViewModel {

    private String country;
    private BigDecimal salary;
    private LocalDate rateEffectiveDate;

}
