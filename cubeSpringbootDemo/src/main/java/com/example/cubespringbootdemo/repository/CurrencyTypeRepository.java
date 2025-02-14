package com.example.cubespringbootdemo.repository;


import com.example.cubespringbootdemo.entity.CurrencyTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CurrencyTypeRepository extends JpaRepository<CurrencyTypeEntity, Integer>, JpaSpecificationExecutor<CurrencyTypeEntity> {

    List<CurrencyTypeEntity> getAllByCurrencyCHNameOrCurrencyCode(String currencyCHName, String currencyCode);

    List<CurrencyTypeEntity> getAllByCurrencyCHName(String currencyCHName);

    List<CurrencyTypeEntity> getAllByCurrencyCode(String currencyCode);

    boolean existsByCurrencyCode(String currencyCode);

}
