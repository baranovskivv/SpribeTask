package com.spribe.testTaskForSeniorJavaDeveloper.configuration;

import com.spribe.testTaskForSeniorJavaDeveloper.service.CurrencyRateService;
import com.spribe.testTaskForSeniorJavaDeveloper.service.CurrencyService;
import com.spribe.testTaskForSeniorJavaDeveloper.service.DataUpdateService;
import com.spribe.testTaskForSeniorJavaDeveloper.service.DataUpdateServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyUpdateConfig {

    private final CurrencyRateService currencyRateService;
    private final CurrencyService currencyService;

    public CurrencyUpdateConfig(CurrencyRateService currencyRateService, CurrencyService currencyService) {
        this.currencyRateService = currencyRateService;
        this.currencyService = currencyService;

    }

    @Bean
    public DataUpdateService dataUpdateServiceRun() {
        DataUpdateService dataUpdateService = new DataUpdateServiceImpl(currencyRateService, currencyService);
        dataUpdateService.runScheduler();
        return dataUpdateService;
    }
}
