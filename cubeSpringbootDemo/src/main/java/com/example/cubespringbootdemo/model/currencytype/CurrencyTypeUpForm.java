package com.example.cubespringbootdemo.model.currencytype;

import lombok.Data;

@Data
public class CurrencyTypeUpForm extends CurrencyTypeCrForm {

    /** 主鍵 */
    private Integer id;

    private String rowStatus="U";
}
