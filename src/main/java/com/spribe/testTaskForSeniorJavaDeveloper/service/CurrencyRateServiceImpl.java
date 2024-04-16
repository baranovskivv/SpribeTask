package com.spribe.testTaskForSeniorJavaDeveloper.service;

import com.spribe.testTaskForSeniorJavaDeveloper.dao.CurrencyRatePackRepository;
import com.spribe.testTaskForSeniorJavaDeveloper.dao.CurrencyRateRepository;
import com.spribe.testTaskForSeniorJavaDeveloper.dao.CurrencyRepository;
import com.spribe.testTaskForSeniorJavaDeveloper.dao.OuterSystemRepository;
import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyDTO;
import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyRateDTO;
import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyRateResponse;
import com.spribe.testTaskForSeniorJavaDeveloper.model.Currency;
import com.spribe.testTaskForSeniorJavaDeveloper.model.CurrencyRate;
import com.spribe.testTaskForSeniorJavaDeveloper.model.CurrencyRatePack;
import com.spribe.testTaskForSeniorJavaDeveloper.model.OuterSystem;
import com.spribe.testTaskForSeniorJavaDeveloper.model.enam.COuterSystem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CurrencyRateServiceImpl implements CurrencyRateService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyRatePackRepository currencyRatePackRepository;
    private final OuterSystemRepository outerSystemRepository;
    private final CurrencyRateRepository currencyRateRepository;

    public CurrencyRateServiceImpl(CurrencyRepository currencyRepository,
                                   CurrencyRatePackRepository currencyRatePackRepository,
                                   OuterSystemRepository outerSystemRepository,
                                   CurrencyRateRepository currencyRateRepository) {
        this.currencyRepository = currencyRepository;
        this.currencyRatePackRepository = currencyRatePackRepository;
        this.outerSystemRepository = outerSystemRepository;
        this.currencyRateRepository = currencyRateRepository;
    }


    @Override
    @Transactional
    public void getAndSaveCurrencyRates(String url) throws Exception {
        CurrencyRateResponse currencyRateResponse = getCurrencyRateResponse(url);
        List<CurrencyRate> allCurrencyRatesFromCurrencyRateResponse = getAllCurrencyRatesFromCurrencyRateResponse(currencyRateResponse);
        processAndSaveCurrencyRates(allCurrencyRatesFromCurrencyRateResponse, currencyRateResponse);
    }

    @Override
    public void processAndSaveCurrencyRates(List<CurrencyRate> currencyRates, CurrencyRateResponse currencyRateResponse) {
        CurrencyRatePack lastCurrencyRatePack = currencyRatePackRepository.findFirstByOrderByStartDate();
        if (Objects.nonNull(lastCurrencyRatePack)) {
            if (!Objects.equals(lastCurrencyRatePack.getStartDate(), currencyRateResponse.getStartDate())) {
                saveCurrencyRatesWithPack(currencyRates, currencyRateResponse);
            }
        } else {
            saveCurrencyRatesWithPack(currencyRates, currencyRateResponse);
        }
    }

    @Override
    public void saveCurrencyRatesWithPack(List<CurrencyRate> currencyRates, CurrencyRateResponse currencyRateResponse) {
        OuterSystem outerSystem = outerSystemRepository.getByCode(COuterSystem.OXR.name());
        CurrencyRatePack currencyRatePack = currencyRatePackRepository.save(
                CurrencyRatePack.builder().
                        outerSystem(outerSystem)
                        .startDate(currencyRateResponse.getStartDate())
                        .unloadDate(new Date())
                        .build());

        for (CurrencyRate currencyRate : currencyRates) {
            currencyRate.setCurrencyRatePack(currencyRatePack);
            currencyRateRepository.save(currencyRate);
        }
    }

    @Override
    public List<CurrencyRate> getAllCurrencyRatesFromCurrencyRateResponse(CurrencyRateResponse currencyRateResponse) {

        if (Objects.nonNull(currencyRateResponse)) {
            List<CurrencyRate> currencyRates = new ArrayList<>();

            Currency baseCurrency = currencyRepository.getCurrencyByCode(currencyRateResponse.getBaseCurrencyCode());

            for (Map.Entry<String, BigDecimal> entry : currencyRateResponse.getToCurrencyRateMap().entrySet()) {
                String toCurrencyCode = entry.getKey();
                BigDecimal rate = entry.getValue();

                CurrencyRate currencyRate = CurrencyRate.builder()
                        .fromCurrency(baseCurrency)
                        .toCurrency(currencyRepository.getCurrencyByCode(toCurrencyCode))
                        .rate(rate)
                        .build();
                currencyRates.add(currencyRate);
            }

            return currencyRates;
        }
        return null;
    }

    @Override
    public CurrencyRateResponse getCurrencyRateResponse(String url) throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        CurrencyRateResponse response = restTemplate.getForObject(url, CurrencyRateResponse.class);
        if (Objects.isNull(response))
            throw new Exception("Sorry! Server is not available!");
        return response;
    }

    @Override
    public List<CurrencyRateDTO> getLastCurrencyRateDTOs() {
        final CurrencyRatePack currencyRatePack = currencyRatePackRepository.findFirstByOrderByStartDate();
        List<CurrencyRate> currencyRates = currencyRateRepository.getByCurrencyRatePack(currencyRatePack);
       return currencyRates.stream()
                .map(CurrencyRate::toDTO).collect(Collectors.toList());
    }

}
