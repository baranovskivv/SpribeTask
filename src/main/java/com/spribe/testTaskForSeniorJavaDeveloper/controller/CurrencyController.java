package com.spribe.testTaskForSeniorJavaDeveloper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spribe.testTaskForSeniorJavaDeveloper.configuration.CurrencyUpdateConfig;
import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyDTO;
import com.spribe.testTaskForSeniorJavaDeveloper.exception.CurrencyRateTaskException;
import com.spribe.testTaskForSeniorJavaDeveloper.service.CurrencyService;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("currency")
@Log4j2
public class CurrencyController {

    private final CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }


    @PostMapping(value = "currencyUpdate", produces = "application/json")
    public ResponseEntity<String> updateCurrencies() {
        try {
            currencyService.getAndSaveNewCurrencies();
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Currencies is updated");
    }

    @GetMapping(value = "currencies", produces = "application/json")
    public ResponseEntity<List<CurrencyDTO>> getCurrencies() {
        List<CurrencyDTO> currencies = currencyService.getCurrencyDTOs();
        return ResponseEntity.ok(currencies);
    }

    @PostMapping(value = "addCurrency/{currencyCode}", produces = "application/json")
    public ResponseEntity<String> addCurrencyForUpdating(@PathVariable String currencyCode) {
        try {
            currencyService.addCurrencyToUpdating(currencyCode);
        } catch (CurrencyRateTaskException e) {
            log.error(e.getMessage());
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Added currency");
    }


}
