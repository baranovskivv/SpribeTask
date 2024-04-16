package com.spribe.testTaskForSeniorJavaDeveloper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spribe.testTaskForSeniorJavaDeveloper.dao.CurrencyRepository;
import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyDTO;
import com.spribe.testTaskForSeniorJavaDeveloper.model.Currency;
import com.spribe.testTaskForSeniorJavaDeveloper.model.enam.CCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public void getAndSaveNewCurrencies() throws JsonProcessingException {
        String currencyStringResponse = getCurrencyStringResponse();
        saveUpdatedCurrency(currencyStringResponse);
    }

    @Override
    public String getCurrencyStringResponse() {
        final RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(CCommand.GET_CURRENCIES.getUrl(), String.class);
    }

    @Override
    public void saveUpdatedCurrency(String currencyStringResponse) throws JsonProcessingException {

        Map<String, String> curCodeCurNameMap = new ObjectMapper().readValue(currencyStringResponse, HashMap.class);

        for (Map.Entry<String, String> entry : curCodeCurNameMap.entrySet()) {
            String curCode = entry.getKey();
            String curName = entry.getValue();

            Currency currencyFromDb = currencyRepository.getCurrencyByCode(curCode);

            if (Objects.nonNull(currencyFromDb) && !Objects.equals(currencyFromDb.getName(), curName)) {
                currencyFromDb.setName(curName);
                currencyRepository.save(currencyFromDb);
            } else if (currencyFromDb == null) {
                currencyRepository.save(new Currency(null, curCode, curName, false));
            }
        }

    }

    @Override
    public List<CurrencyDTO> getCurrencyDTOs() {
        return currencyRepository.findAll().stream()
                .map(Currency::toDTO).collect(Collectors.toList());
    }

    @Override
    public void addCurrencyToUpdating(String currencyCode) throws Exception {

        Currency currencyByCode = currencyRepository.getCurrencyByCode(currencyCode);
        if (Objects.nonNull(currencyByCode)) {
            currencyByCode.setUsed(true);
            currencyRepository.save(currencyByCode);
        } else {
            throw new Exception("Currency is not exist");
        }

    }

    @Override
    public String getCurrenciesForUpdating() {
        return currencyRepository.getByUsedTrue()
                .stream()
                .map(Currency::getCode)
                .collect(Collectors.joining(","));
    }


}
