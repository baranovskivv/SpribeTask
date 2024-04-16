package com.spribe.testTaskForSeniorJavaDeveloper.controller;

import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyRateDTO;
import com.spribe.testTaskForSeniorJavaDeveloper.model.enam.CCommand;
import com.spribe.testTaskForSeniorJavaDeveloper.service.CurrencyRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("currencyRates")
public class CurrencyRateController {

    private CurrencyRateService сurrencyRateService;

    @Autowired
    public CurrencyRateController(CurrencyRateService currencyRateService) {
        this.сurrencyRateService = currencyRateService;
    }

    @GetMapping(value = "updateCurRates", produces = "application/json")
    public ResponseEntity<String> updateCurrencyRates() {
        try {
            сurrencyRateService.getAndSaveCurrencyRates(CCommand.GET_CURRENCY_RATES.getUrl().concat("d008d27d32b945dca5582a4beb35d4cb"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(e.getMessage());
        }
        return ResponseEntity.ok()
                .body("Currencies is updated");
    }

    @GetMapping(value = "currencyRates", produces = "application/json")
    public ResponseEntity<List<CurrencyRateDTO>> getCurrencyRates() {

        List<CurrencyRateDTO> lastCurrencyRateDTOs = сurrencyRateService.getLastCurrencyRateDTOs();

        return ResponseEntity.ok(lastCurrencyRateDTOs);
    }

}
