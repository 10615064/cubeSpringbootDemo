package com.example.cubespringbootdemo.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 幣別
 *
 */

@Data
@Entity
@Table(name="CURRENCY_TYPE")
public class CurrencyTypeEntity {

    /** 主鍵 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** 版本 */
    @Column(name = "VERSION")
    @Version
    private Integer version;

    /** 幣別(英文) */
    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    /** 幣別(中文) */
    @Column(name = "CURRENCY_CH_NAME")
    private String currencyCHName;
}
