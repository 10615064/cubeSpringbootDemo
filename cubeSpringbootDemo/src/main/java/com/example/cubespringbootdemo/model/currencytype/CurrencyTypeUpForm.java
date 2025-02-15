package com.example.cubespringbootdemo.model.currencytype;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CurrencyTypeUpForm extends CurrencyTypeCrForm {

    /** 主鍵 */
    private Integer id;

    @JsonProperty("rowStatus")
    @Schema(defaultValue = "U")
    private String rowStatus = "U";
}
