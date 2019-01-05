package com.anteklantek.SalaryCalculator.service;

import com.anteklantek.SalaryCalculator.client.ExchangeClient;
import com.anteklantek.SalaryCalculator.client.viewmodel.RateViewModel;
import com.anteklantek.SalaryCalculator.controller.viewmodel.SalaryViewModel;
import com.anteklantek.SalaryCalculator.properties.CalculateSalaryProperties;
import com.anteklantek.SalaryCalculator.repository.CountryRepository;
import com.anteklantek.SalaryCalculator.controller.viewmodel.SalaryListViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;

@Service
public class SalaryCalculatorService {

    @Autowired
    CalculateSalaryProperties calculateSalaryProperties;

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    ExchangeClient exchangeClient;

    public SalaryListViewModel getMonthSalariesForDayGrossSalary(BigDecimal dayGrossSalary){

        SalaryViewModel salaryViewModel = new SalaryViewModel();
        salaryViewModel.setCountry("Great Britian");
        salaryViewModel.setSalary(dayGrossSalary.multiply(new BigDecimal("22")));
        return new SalaryListViewModel(Collections.singletonList(salaryViewModel));
    }



}
