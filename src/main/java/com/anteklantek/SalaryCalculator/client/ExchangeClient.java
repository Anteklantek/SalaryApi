package com.anteklantek.SalaryCalculator.client;

import com.anteklantek.SalaryCalculator.client.viewmodel.RateViewModel;
import com.anteklantek.SalaryCalculator.properties.ExchangeRateApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Slf4j
public class ExchangeClient {

    @Autowired
    private ExchangeRateApiProperties exchangeRateApiProperties;

    /**
     * Fetches currency rate from exchange microservice
     * @param code code of currency to obtain data about
     * @return RateViewModel representing currency, rate and date of rate
     */
    public RateViewModel getExchangeRateByCode(String code) {
        final RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(exchangeRateApiProperties.getUrl())
                .queryParam("code", code);
        ResponseEntity<RateViewModel> response = restTemplate.getForEntity(
                builder.toUriString(),
                RateViewModel.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            log.error("Error while communicating with exchange microservice" + response.toString());
            return null;
        }
    }
}