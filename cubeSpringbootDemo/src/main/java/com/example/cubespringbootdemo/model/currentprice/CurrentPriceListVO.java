package com.example.cubespringbootdemo.model.currentprice;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class CurrentPriceListVO implements Serializable {

    /** 主鍵 */
    private String id;

    /** 美金匯換台幣匯率 */
    private BigDecimal usdToNtd;

    /** 匯率日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date exchangeDate;
}
