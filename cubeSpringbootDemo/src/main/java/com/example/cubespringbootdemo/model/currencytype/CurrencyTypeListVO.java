package com.example.cubespringbootdemo.model.currencytype;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CurrencyTypeListVO implements Serializable {

    /** 主鍵 */
    private Integer id;

    /** 幣別(英文) */
    private String currencyCode;

    /** 幣別(中文) */
    private String currencyCHName;

    /** 版本 */
    private Integer version;
}
