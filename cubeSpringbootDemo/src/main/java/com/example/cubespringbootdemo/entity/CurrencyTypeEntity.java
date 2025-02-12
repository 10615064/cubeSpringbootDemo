package com.example.cubespringbootdemo.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name="CURRENCY_TYPE")
public class CurrencyTypeEntity {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 幣別(英文) */
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    /** 幣別(中文) */
    @Column(name = "CURRENCY_CH_NAME")
    private String currencyCHName;
}
