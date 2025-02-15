package com.example.cubespringbootdemo.model.currencytype;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CurrencyTypePagination {

    @Schema(defaultValue = "1", description = "當前頁碼，預設為 1")
    private int page = 1;

    @Schema(defaultValue = "10", description = "每頁顯示筆數，預設為 10")
    private int size = 10;

    /** 主鍵 */
    private Integer id;

    /** 幣別(英文) */
    private String currencyCode;

    /** 幣別(中文) */
    private String currencyCHName;
}
