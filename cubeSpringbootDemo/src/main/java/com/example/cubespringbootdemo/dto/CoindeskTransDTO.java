package com.example.cubespringbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoindeskTransDTO {

    /** 幣別 */
    @JsonProperty("currencyCode")
    private String currencyCode;

    /** 幣別(中文) */
    @JsonProperty("currencyCHName")
    private String currencyCHName;

    /** 匯率 */
    @JsonProperty("rate")
    private String rate;

    /** 浮動匯率 */
    @JsonProperty("rate_float")
    private double rateFloat;


}
