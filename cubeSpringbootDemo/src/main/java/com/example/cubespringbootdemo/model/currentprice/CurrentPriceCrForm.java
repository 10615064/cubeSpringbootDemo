package com.example.cubespringbootdemo.model.currentprice;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class CurrentPriceCrForm {



    /** 美金匯換台幣匯率 */
    private BigDecimal usdToNtd;

    /** 匯率日期 */
    private Date exchangeDate;
}
