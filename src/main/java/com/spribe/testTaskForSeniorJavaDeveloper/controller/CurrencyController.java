package com.spribe.testTaskForSeniorJavaDeveloper.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyDTO;
import com.spribe.testTaskForSeniorJavaDeveloper.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("currency")
public class CurrencyController {

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "currencyUpdate", produces = "application/json")
    public ResponseEntity<String> updateCurrencies() {
        try {
            currencyService.getAndSaveNewCurrencies();
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Currencies is updated");
    }

    @GetMapping(value = "currencies", produces = "application/json")
    public ResponseEntity<List<CurrencyDTO>> getCurrencies() {
        List<CurrencyDTO> currencies = currencyService.getCurrencyDTOs();
        return ResponseEntity.ok(currencies);
    }

    @GetMapping(value = "addCurrency/{currencyCode}", produces = "application/json")
    public ResponseEntity<String> addCurrencyForUpdating(@PathVariable String currencyCode) {
        try {
            currencyService.addCurrencyToUpdating(currencyCode);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
        return ResponseEntity.ok("Added currency");
    }


}
