package com.anteklantek.SalaryCalculator.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties("calculate-salary")
public class CalculateSalaryProperties {

    private List<String> countries;
}
