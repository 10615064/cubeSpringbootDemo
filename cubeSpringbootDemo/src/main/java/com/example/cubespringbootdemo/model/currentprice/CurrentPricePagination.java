package com.example.cubespringbootdemo.model.currentprice;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class CurrentPricePagination {

    private int page;

    private int size;

    /** 主鍵 */
    private String id;

    /** 美金匯換台幣匯率 */
    private BigDecimal usdToNtd;

    /** 匯率日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exchangeDate;
}
