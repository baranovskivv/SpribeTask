package com.spribe.testTaskForSeniorJavaDeveloper.configuration;

import com.spribe.testTaskForSeniorJavaDeveloper.service.CurrencyRateService;
import com.spribe.testTaskForSeniorJavaDeveloper.service.CurrencyService;
import com.spribe.testTaskForSeniorJavaDeveloper.service.DataUpdateService;
import com.spribe.testTaskForSeniorJavaDeveloper.service.DataUpdateServiceImpl;
import com.spribe.testTaskForSeniorJavaDeveloper.util.EnvironmentUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class CurrencyUpdateConfig {

    private final CurrencyRateService currencyRateService;
    private final CurrencyService currencyService;
    private final EnvironmentUtil environmentUtil;

    public CurrencyUpdateConfig(CurrencyRateService currencyRateService, CurrencyService currencyService, EnvironmentUtil environmentUtil) {
        this.currencyRateService = currencyRateService;
        this.currencyService = currencyService;
        this.environmentUtil = environmentUtil;
    }

    @Bean
    public DataUpdateService dataUpdateServiceRun() {
        DataUpdateService dataUpdateService = new DataUpdateServiceImpl(currencyRateService, currencyService, environmentUtil);
        dataUpdateService.runScheduler();
        return dataUpdateService;
    }
}
