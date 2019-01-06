package com.anteklantek.SalaryCalculator.service;

import com.anteklantek.SalaryCalculator.client.ExchangeClient;
import com.anteklantek.SalaryCalculator.client.viewmodel.RateViewModel;
import com.anteklantek.SalaryCalculator.controller.viewmodel.SalaryListViewModel;
import com.anteklantek.SalaryCalculator.controller.viewmodel.SalaryViewModel;
import com.anteklantek.SalaryCalculator.model.Country;
import com.anteklantek.SalaryCalculator.properties.CalculateSalaryProperties;
import com.anteklantek.SalaryCalculator.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class SalaryCalculatorService {

    @Autowired
    private CalculateSalaryProperties calculateSalaryProperties;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ExchangeClient exchangeClient;

    public SalaryListViewModel getMonthSalariesForDayGrossSalary(BigDecimal dayGrossSalary) {
        SalaryListViewModel salaryListViewModel = new SalaryListViewModel();
        for (String countryName : calculateSalaryProperties.getCountries()) {
            Country country = countryRepository.findFirstByName(countryName);
            RateViewModel rateViewModel = exchangeClient.getExchangeRateByCode(country.getCurrencyCode());
            BigDecimal monthSalary = calculateMonthSalary(dayGrossSalary, country, rateViewModel);
            SalaryViewModel salaryViewModel = new SalaryViewModel(countryName, monthSalary, rateViewModel.getTableEffectiveDate());
            salaryListViewModel.getSalaryItemsList().add(salaryViewModel);
        }

        return salaryListViewModel;
    }

    private BigDecimal calculateMonthSalary(BigDecimal dayGrossSalary, Country country, RateViewModel rateViewModel) {
        BigDecimal monthSalaryAfterFixedCostAndTaxes = getMonthSalaryAfterFixedCostAndTax(dayGrossSalary, country);
        return getMonthSalaryInPLN(rateViewModel.getRateValue(), monthSalaryAfterFixedCostAndTaxes);
    }

    private BigDecimal getMonthSalaryAfterFixedCostAndTax(BigDecimal dayGrossSalary, Country country) {
        BigDecimal monthSalary = dayGrossSalary.multiply(BigDecimal.valueOf(22));
        BigDecimal monthSalaryAfterFixedCost = monthSalary.subtract(country.getFixedCost());
        BigDecimal tax = monthSalaryAfterFixedCost.multiply(BigDecimal.valueOf(country.getTax())).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        return monthSalaryAfterFixedCost.subtract(tax);
    }

    private BigDecimal getMonthSalaryInPLN(BigDecimal rateValue, BigDecimal monthSalaryAfterFixedCostAndTaxes) {
        BigDecimal monthSalaryInPLN = monthSalaryAfterFixedCostAndTaxes.multiply(rateValue);
        return monthSalaryInPLN.setScale(2, RoundingMode.HALF_UP);
    }
}
