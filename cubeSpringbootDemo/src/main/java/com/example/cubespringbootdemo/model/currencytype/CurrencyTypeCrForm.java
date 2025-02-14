package com.example.cubespringbootdemo.model.currencytype;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class CurrencyTypeCrForm {



    /** 幣別(英文) */
    @NotBlank(message = "幣別不能為空")
    @JsonProperty("currencyCode")
    private String currencyCode;

    /** 幣別(中文) */
    @JsonProperty("currencyCHName")
    private String currencyCHName;

    /** 版本 */
    @JsonProperty("version")
    private Integer version;

    private String rowStatus="C";

}
