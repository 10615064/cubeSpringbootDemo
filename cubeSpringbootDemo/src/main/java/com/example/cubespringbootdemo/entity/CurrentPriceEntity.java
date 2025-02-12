package com.example.cubespringbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
@Entity
@Table(name="CURRENT_PRICE")
public class CurrentPriceEntity {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 美金匯換台幣匯率 */
    @Column(name = "USD_TO_NTD")
    private BigDecimal usdToNtd;

    /** 匯率日期 */
    @Column(name = "EXCHANGE_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "GMT+8")
    private Date exchangeDate;
}
