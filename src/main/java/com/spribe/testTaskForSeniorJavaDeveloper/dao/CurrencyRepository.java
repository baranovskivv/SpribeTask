package com.spribe.testTaskForSeniorJavaDeveloper.dao;

import com.spribe.testTaskForSeniorJavaDeveloper.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency getCurrencyByCode(String code);

    List<Currency> getByUsedTrue();

}
