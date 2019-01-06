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

    public static final int NUMBER_OF_PAID_DAYS_IN_MONTH = 22;

    @Autowired
    private CalculateSalaryProperties calculateSalaryProperties;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private ExchangeClient exchangeClient;

    /**
     * calculates month (22 day) salary in pln given day gross salary for countries
     * specifed as calculate-salary.countries in application.properites
     * rate of currency conversion is obtained from exchchange microservice throug ExchangeClient
     * and tax and fixed cost of country is obtained from database
     * @param dayGrossSalary one day salary to calculate month salary
     * @return model conataining List of SalaryViewModel of each country
     */
    public SalaryListViewModel getMonthSalariesForDayGrossSalary(BigDecimal dayGrossSalary) {
        SalaryListViewModel salaryListViewModel = new SalaryListViewModel();
        for (String countryName : calculateSalaryProperties.getCountries()) {
            Country country = countryRepository.findFirstByName(countryName);
            RateViewModel rateViewModel = exchangeClient.getExchangeRateByCode(country.getCurrencyCode());
            BigDecimal monthSalary = calculateMonthSalaryInPLN(dayGrossSalary, country, rateViewModel.getRateValue());
            SalaryViewModel salaryViewModel = new SalaryViewModel(countryName, monthSalary, rateViewModel.getTableEffectiveDate());
            salaryListViewModel.getSalaryItemsList().add(salaryViewModel);
        }

        return salaryListViewModel;
    }

    private BigDecimal calculateMonthSalaryInPLN(BigDecimal dayGrossSalary, Country country, BigDecimal rateValue) {
        BigDecimal monthSalaryAfterFixedCostAndTaxes = getMonthSalaryAfterFixedCostAndTax(dayGrossSalary, country);
        BigDecimal monthSalaryInPLN = convertToPln(rateValue, monthSalaryAfterFixedCostAndTaxes);
        return monthSalaryInPLN;
    }

    private BigDecimal getMonthSalaryAfterFixedCostAndTax(BigDecimal dayGrossSalary, Country country) {
        BigDecimal monthSalary = dayGrossSalary.multiply(BigDecimal.valueOf(NUMBER_OF_PAID_DAYS_IN_MONTH));
        BigDecimal monthSalaryAfterFixedCost = monthSalary.subtract(country.getFixedCost());
        BigDecimal tax = monthSalaryAfterFixedCost.multiply(BigDecimal.valueOf(country.getTax())).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        return monthSalaryAfterFixedCost.subtract(tax);
    }

    private BigDecimal convertToPln(BigDecimal rateValue, BigDecimal monthSalaryAfterFixedCostAndTaxes) {
        BigDecimal monthSalaryInPLN = monthSalaryAfterFixedCostAndTaxes.multiply(rateValue);
        return monthSalaryInPLN.setScale(2, RoundingMode.HALF_UP);
    }
}
