package com.anteklantek.SalaryCalculator;

import com.anteklantek.SalaryCalculator.client.viewmodel.RateViewModel;
import com.anteklantek.SalaryCalculator.model.Country;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestObjectsFactory {

    public static RateViewModel getPLNRateViewModel(){
        return new RateViewModel("PLN", BigDecimal.ONE, LocalDate.of(2010,10,10));
    }
    public static RateViewModel getEURRateViewModel(){
        return new RateViewModel("EUR", new BigDecimal("4.3018"), LocalDate.of(2010,10,10));
    }
    public static RateViewModel getGBPRateViewModel(){
        return new RateViewModel("GPB", new BigDecimal("4.7803"), LocalDate.of(2010,10,10));
    }

    public static Country getUnitedKingdom() {
        return  new Country(100L,"United Kingdom", BigDecimal.valueOf(600),25,"GBP");
    }

    public static Country getGermany() {
        return  new Country(101L,"Germany", BigDecimal.valueOf(800),20,"EUR");
    }

    public static Country getPoland() {
        return  new Country(101L,"Poland", BigDecimal.valueOf(1200),19,"PLN");
    }
}
