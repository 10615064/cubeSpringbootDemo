package com.example.cubespringbootdemo.utils;


public enum CurrencyUtil {

    USD("美元"),
    GBP("英鎊"),
    EUR("歐元");

    private final String chineseName;

    CurrencyUtil(String chineseName) {
        this.chineseName = chineseName;
    }

    private String getChineseName() {
        return chineseName;
    }

    //根據貨幣代碼獲取中文名稱
    public static String getChineseNameByCode(String code) {
        for (CurrencyUtil currency : values()) {
            if (currency.name().equals(code)) {
                return currency.getChineseName();
            }
        }
        //沒有找到對應的中文名稱，則返回原代碼
        return code;
    }
}
