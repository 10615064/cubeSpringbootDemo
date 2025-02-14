package com.example.cubespringbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeInfoDTO {

    @JsonProperty("updated")
    private String updated;

    @JsonProperty("updatedISO")
    private String updatedISO;

    @JsonProperty("updateduk")
    private String updateduk;
}
