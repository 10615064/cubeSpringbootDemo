package com.example.cubespringbootdemo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoindeskTransDataDTO {

    @JsonProperty("time")
    private TimeInfoDTO time;

    @JsonProperty("coindeskTransDTOList")
    List<CoindeskTransDTO> coindeskTransDTOList;


}
