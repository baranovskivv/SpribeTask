package com.spribe.testTaskForSeniorJavaDeveloper.service;

import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyRateDTO;
import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyRateResponse;
import com.spribe.testTaskForSeniorJavaDeveloper.exception.CurrencyRateTaskException;
import com.spribe.testTaskForSeniorJavaDeveloper.model.CurrencyRate;

import java.util.List;


public interface CurrencyRateService {

   void getAndSaveCurrencyRates(String url) throws CurrencyRateTaskException;

   void saveCurrencyRatesWithPack(List<CurrencyRate> currencyRates, CurrencyRateResponse currencyRateResponse);

   void processAndSaveCurrencyRates(List<CurrencyRate> currencyRates, CurrencyRateResponse currencyRateResponse);

   List<CurrencyRate> getAllCurrencyRatesFromCurrencyRateResponse(CurrencyRateResponse currencyRateResponse);

   CurrencyRateResponse getCurrencyRateResponse(String url) throws CurrencyRateTaskException;

   List<CurrencyRateDTO> getLastCurrencyRateDTOs();

}
