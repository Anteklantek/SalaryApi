package com.anteklantek.SalaryCalculator;

import com.anteklantek.SalaryCalculator.client.ExchangeClient;
import com.anteklantek.SalaryCalculator.controller.viewmodel.SalaryListViewModel;
import com.anteklantek.SalaryCalculator.controller.viewmodel.SalaryViewModel;
import com.anteklantek.SalaryCalculator.properties.CalculateSalaryProperties;
import com.anteklantek.SalaryCalculator.repository.CountryRepository;
import com.anteklantek.SalaryCalculator.service.SalaryCalculatorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SalaryCalculatorServiceTest {

    @Mock
    private CalculateSalaryProperties calculateSalaryProperties;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private ExchangeClient exchangeClient;

    @InjectMocks
    SalaryCalculatorService salaryCalculatorService;

    @Before
    public void setUp() {
        when(calculateSalaryProperties.getCountries()).thenReturn(Arrays.asList("United Kingdom","Germany","Poland"));

        when(exchangeClient.getExchangeRateByCode("PLN")).thenReturn(TestObjectsFactory.getPLNRateViewModel());
        when(exchangeClient.getExchangeRateByCode("EUR")).thenReturn(TestObjectsFactory.getEURRateViewModel());
        when(exchangeClient.getExchangeRateByCode("GBP")).thenReturn(TestObjectsFactory.getGBPRateViewModel());

        when(countryRepository.findFirstByName("United Kingdom")).thenReturn(TestObjectsFactory.getUnitedKingdom());
        when(countryRepository.findFirstByName("Germany")).thenReturn(TestObjectsFactory.getGermany());
        when(countryRepository.findFirstByName("Poland")).thenReturn(TestObjectsFactory.getPoland());
    }

    @Test
    public void verifyProperSalaryIsCalculated() {
        SalaryListViewModel salaryListViewModelActual = salaryCalculatorService.getMonthSalariesForDayGrossSalary(new BigDecimal("213"));
        SalaryViewModel salaryViewModel1 = new SalaryViewModel("United Kingdom",new BigDecimal("14649.23"), LocalDate.of(2010,10,10));
        SalaryViewModel salaryViewModel2 = new SalaryViewModel("Germany",new BigDecimal("13373.44"), LocalDate.of(2010,10,10));
        SalaryViewModel salaryViewModel3 = new SalaryViewModel("Poland",new BigDecimal("2823.66"), LocalDate.of(2010,10,10));
        List actualSalaries = new ArrayList<>(Arrays.asList(salaryViewModel1, salaryViewModel2, salaryViewModel3));
        assertThat("List equality without order",
                salaryListViewModelActual.getSalaryItemsList(), containsInAnyOrder(actualSalaries.toArray()));
    }


}
