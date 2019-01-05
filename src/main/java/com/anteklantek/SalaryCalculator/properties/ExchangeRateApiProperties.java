package com.anteklantek.SalaryCalculator.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("exchange-rate-api")
public class ExchangeRateApiProperties {

    private String url;
}
