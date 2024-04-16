package com.spribe.testTaskForSeniorJavaDeveloper.service;

import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyRateResponse;
import com.spribe.testTaskForSeniorJavaDeveloper.model.CurrencyRate;
import com.spribe.testTaskForSeniorJavaDeveloper.model.enam.CCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class DataUpdateServiceImpl implements DataUpdateService {

    private final CurrencyRateService currencyRateService;
    private final CurrencyService currencyService;

    @Autowired
    public DataUpdateServiceImpl(CurrencyRateService currencyRateService, CurrencyService currencyService) {
        this.currencyRateService = currencyRateService;
        this.currencyService = currencyService;
    }


    public void updateData() {
        try {
           currencyRateService.getAndSaveCurrencyRates(
                   CCommand.GET_CURRENCY_RATES.getUrl().concat("d008d27d32b945dca5582a4beb35d4cb")
           .concat("&".concat(currencyService.getCurrenciesForUpdating()))
           );

        } catch (Exception e) {
            System.out.println("Currency rates updating is failed");
            e.printStackTrace();
        }
        System.out.println("Currency rates are updated");
    }

    public void runScheduler() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::updateData, 0, 2, TimeUnit.HOURS);
    }

}
