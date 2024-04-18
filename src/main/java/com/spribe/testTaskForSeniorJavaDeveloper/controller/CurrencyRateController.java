package com.spribe.testTaskForSeniorJavaDeveloper.controller;

import com.spribe.testTaskForSeniorJavaDeveloper.dto.CurrencyRateDTO;
import com.spribe.testTaskForSeniorJavaDeveloper.exception.CurrencyRateTaskException;
import com.spribe.testTaskForSeniorJavaDeveloper.model.enums.CCommand;
import com.spribe.testTaskForSeniorJavaDeveloper.service.CurrencyRateService;
import com.spribe.testTaskForSeniorJavaDeveloper.util.EnvironmentUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController("currencyRates")
@Log4j2
public class CurrencyRateController {

    private final CurrencyRateService сurrencyRateService;
    private final EnvironmentUtil environmentUtil;

    @Autowired
    public CurrencyRateController(CurrencyRateService currencyRateService, EnvironmentUtil environmentUtil) {
        this.сurrencyRateService = currencyRateService;
        this.environmentUtil = environmentUtil;
    }

    @PostMapping(value = "updateCurRates", produces = "application/json")
    public ResponseEntity<String> updateCurrencyRates() {
        try {
            сurrencyRateService.getAndSaveCurrencyRates(CCommand.GET_CURRENCY_RATES.getUrl().concat(environmentUtil.getAppId()));
        } catch (CurrencyRateTaskException e) {
            log.error(e.getMessage());
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
