package com.spribe.testTaskForSeniorJavaDeveloper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spribe.testTaskForSeniorJavaDeveloper.exception.CurrencyRateTaskException;
import com.spribe.testTaskForSeniorJavaDeveloper.model.enums.CCommand;
import com.spribe.testTaskForSeniorJavaDeveloper.util.EnvironmentUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class DataUpdateServiceImpl implements DataUpdateService {

    private final CurrencyRateService currencyRateService;
    private final CurrencyService currencyService;
    private final EnvironmentUtil environmentUtil;

    @Autowired
    public DataUpdateServiceImpl(CurrencyRateService currencyRateService, CurrencyService currencyService, EnvironmentUtil environmentUtil) {
        this.currencyRateService = currencyRateService;
        this.currencyService = currencyService;
        this.environmentUtil = environmentUtil;
    }

    @Transactional
    public void updateData() {
        try {
           currencyService.getAndSaveNewCurrencies();
           currencyRateService.getAndSaveCurrencyRates(
                   CCommand.GET_CURRENCY_RATES.getUrl().concat(environmentUtil.getAppId())
//           .concat("&".concat(currencyService.getCurrenciesForUpdating()))
           );

        } catch (JsonProcessingException | CurrencyRateTaskException e) {
            log.error(e.getMessage());
        }
        log.info("Currency rates are updated");
    }

    public void runScheduler() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::updateData, 0, 2, TimeUnit.HOURS);
    }

}
