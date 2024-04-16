package com.spribe.testTaskForSeniorJavaDeveloper.dao;

import com.spribe.testTaskForSeniorJavaDeveloper.model.CurrencyRatePack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface CurrencyRatePackRepository extends JpaRepository<CurrencyRatePack, Long> {

    CurrencyRatePack findFirstByOrderByStartDate();

}
