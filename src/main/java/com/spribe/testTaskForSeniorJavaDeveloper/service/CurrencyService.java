package com.spribe.testTaskForSeniorJavaDeveloper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyDTO;
import com.spribe.testTaskForSeniorJavaDeveloper.model.Currency;


import java.util.List;

public interface CurrencyService {
    String getCurrencyStringResponse();

    void saveUpdatedCurrency(String currencyStringResponse) throws JsonProcessingException;

    void getAndSaveNewCurrencies() throws JsonProcessingException;

    List<CurrencyDTO> getCurrencyDTOs();

    void addCurrencyToUpdating(String currencyCode) throws Exception;

    String getCurrenciesForUpdating();
}
