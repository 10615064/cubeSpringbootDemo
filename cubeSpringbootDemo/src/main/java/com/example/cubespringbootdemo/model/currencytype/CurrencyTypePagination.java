package com.example.cubespringbootdemo.model.currencytype;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CurrencyTypePagination {

    private int page;

    private int size;

    /** 主鍵 */
    private Integer id;

    /** 幣別(英文) */
    private String currencyCode;

    /** 幣別(中文) */
    private String currencyCHName;
}
