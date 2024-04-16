package com.spribe.testTaskForSeniorJavaDeveloper.dao;

import com.spribe.testTaskForSeniorJavaDeveloper.model.OuterSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OuterSystemRepository extends JpaRepository<OuterSystem, Long> {

    OuterSystem getByCode(String code);

}
