package com.spribe.testTaskForSeniorJavaDeveloper.dao;

import com.spribe.testTaskForSeniorJavaDeveloper.model.CurrencyRate;
import com.spribe.testTaskForSeniorJavaDeveloper.model.CurrencyRatePack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    List<CurrencyRate> getByCurrencyRatePack(CurrencyRatePack currencyRatePack);
}
