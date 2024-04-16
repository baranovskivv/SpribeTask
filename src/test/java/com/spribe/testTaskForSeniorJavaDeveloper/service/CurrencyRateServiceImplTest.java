package com.spribe.testTaskForSeniorJavaDeveloper.service;

import com.spribe.testTaskForSeniorJavaDeveloper.dao.CurrencyRatePackRepository;
import com.spribe.testTaskForSeniorJavaDeveloper.dao.CurrencyRateRepository;
import com.spribe.testTaskForSeniorJavaDeveloper.dao.CurrencyRepository;
import com.spribe.testTaskForSeniorJavaDeveloper.dao.OuterSystemRepository;
import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyRateResponse;
import com.spribe.testTaskForSeniorJavaDeveloper.model.Currency;
import com.spribe.testTaskForSeniorJavaDeveloper.model.enam.CCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CurrencyRateServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;
    @Mock
    private CurrencyRatePackRepository currencyRatePackRepository;
    @Mock
    private OuterSystemRepository outerSystemRepository;
    @Mock
    private CurrencyRateRepository currencyRateRepository;
    @Mock
    private RestTemplate restTemplate;
    private CurrencyRateServiceImpl currencyRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        currencyRateService = new CurrencyRateServiceImpl(currencyRepository, currencyRatePackRepository, outerSystemRepository, currencyRateRepository);
    }

    @Test
    void testGetAllCurrencyRates_WithValidResponse_ShouldReturnCurrencyRatesList() {
        // given
        CurrencyRateResponse response = getCurrencyRateResponse();
        when(restTemplate.getForObject(anyString(), eq(CurrencyRateResponse.class))).thenReturn(response);
        Currency baseCurrency = getCurrency("USD");
        when(currencyRepository.getCurrencyByCode("USD")).thenReturn(baseCurrency);
        Currency euroCurrency = getCurrency("EUR");
        when(currencyRepository.getCurrencyByCode("EUR")).thenReturn(euroCurrency);

        // when
        var currencyRates = currencyRateService.getAllCurrencyRatesFromCurrencyRateResponse(response);

        // then
        assertNotNull(currencyRates);
        assertEquals(1, currencyRates.size());
        assertEquals("USD", currencyRates.get(0).getFromCurrency().getCode());
        assertEquals("EUR", currencyRates.get(0).getToCurrency().getCode());
        assertEquals(BigDecimal.valueOf(0.85), currencyRates.get(0).getRate());
    }

    @Test
    void testGetAllCurrencyRates_WithValidResponse_ShouldReturnNotNull() {
        // given
        CurrencyRateResponse currencyRateResponse = null;

        // when
        var currencyRates = currencyRateService.getAllCurrencyRatesFromCurrencyRateResponse(currencyRateResponse);

        // then
        assertNull(currencyRates);
    }

    @Test
    void testGetCurrencyRateResponse_WithValidResponse_ShouldReturnValidResponse() {
        // given
        String url = CCommand.GET_CURRENCY_RATES.getUrl().concat("d008d27d32b945dca5582a4beb35d4cb");

        // when
        CurrencyRateResponse currencyRates = null;
        try {
            currencyRates = currencyRateService.getCurrencyRateResponse(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // then
        assertNotNull(currencyRates);
        assertFalse(currencyRates.getToCurrencyRateMap().isEmpty());
        assertTrue(currencyRates.getStartDate().before(new Date()));
    }

    @Test
    void testGetCurrencyRateResponse_WithNullResponse_ShouldReturnNull() {
        // given
        String invalidUrl = CCommand.GET_CURRENCY_RATES.getUrl().concat("d00000000000000000000000000000b");

        // when
        // then
        assertThrows(Exception.class, () -> currencyRateService.getCurrencyRateResponse(invalidUrl));

    }


    private Currency getCurrency(String usd) {
        Currency baseCurrency = new Currency();
        baseCurrency.setCode(usd);
        return baseCurrency;
    }

    private CurrencyRateResponse getCurrencyRateResponse() {
        CurrencyRateResponse response = new CurrencyRateResponse();
        response.setBaseCurrencyCode("USD");
        Map<String, BigDecimal> toCurrencyRateMap = new HashMap<>();
        toCurrencyRateMap.put("EUR", BigDecimal.valueOf(0.85));
        response.setToCurrencyRateMap(toCurrencyRateMap);
        return response;
    }
}