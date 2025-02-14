package com.example.cubespringbootdemo.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoindeskDataDTO {

    @JsonProperty("time")
    private TimeInfoDTO time;

    @JsonProperty("disclaimer")
    private String disclaimer;

    @JsonProperty("chartName")
    private String chartName;

    @JsonProperty("bpi")
    private Map<String, CurrencyDTO> bpi;
}
