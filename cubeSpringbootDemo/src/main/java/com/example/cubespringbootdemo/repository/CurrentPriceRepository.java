package com.example.cubespringbootdemo.repository;

import com.example.cubespringbootdemo.entity.CurrentPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;


public interface CurrentPriceRepository extends JpaRepository<CurrentPriceEntity, Integer>, JpaSpecificationExecutor<CurrentPriceEntity> {

    List<CurrentPriceEntity> getAllByExchangeDate(Date exchangeDate);

}
