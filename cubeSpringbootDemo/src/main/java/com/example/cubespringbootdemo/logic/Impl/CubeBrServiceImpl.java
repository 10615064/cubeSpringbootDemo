package com.example.cubespringbootdemo.logic.Impl;


import com.alibaba.fastjson.JSONObject;
import com.example.cubespringbootdemo.dto.CoindeskDataDTO;
import com.example.cubespringbootdemo.dto.CoindeskTransDataDTO;
import com.example.cubespringbootdemo.dto.CurrencyDTO;
import com.example.cubespringbootdemo.dto.TimeInfoDTO;
import com.example.cubespringbootdemo.feign.CubeCoindeskFeignClient;
import com.example.cubespringbootdemo.logic.CubeBrService;
import com.example.cubespringbootdemo.utils.CurrencyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CubeBrServiceImpl implements CubeBrService {

    @Autowired
    private CubeCoindeskFeignClient cubeCoindeskFeignClient;

    private static final String COINDESK_API_URL = "https://kengp3.github.io/blog/coindesk.json";

    @Override
    public ResponseEntity<CoindeskDataDTO> getCoindeskData() throws Exception {
        try {
            ResponseEntity<CoindeskDataDTO> response = cubeCoindeskFeignClient.getCoindeskData(new URI(COINDESK_API_URL));

            log.info("response: {}", response.getBody());

            return ResponseEntity.ok(response.getBody());
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }
    }

    @Override
    public ResponseEntity<List<CoindeskTransDataDTO>> getCoindeskTransformData() throws Exception {
        try {

            CoindeskDataDTO response = cubeCoindeskFeignClient.getCoindeskData(new URI(COINDESK_API_URL)).getBody();

            // 解析時間
            String updatedISO = response.getTime().getUpdatedISO();
            String formattedTime = formatTime(updatedISO);


            Map<String, CurrencyDTO> bpiObject = response.getBpi();

            // 組織貨幣資料
            List<CoindeskTransDataDTO> transformedData = bpiObject.entrySet().stream()
                    .map(entry -> convertToTransDataDTO(entry, formattedTime))
                    .collect(Collectors.toList());


            return ResponseEntity.ok(transformedData);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }
    }

    private String formatTime(String updatedISO) {
        return OffsetDateTime.parse(updatedISO).format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
    }

    private CoindeskTransDataDTO convertToTransDataDTO(Map.Entry<String, CurrencyDTO> entry, String formattedTime) {
        CoindeskTransDataDTO currencyDTO = new CoindeskTransDataDTO();
        try {
            currencyDTO.setUpdated(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(formattedTime));
        } catch (ParseException e) {
            log.error("轉換錯誤時間訊息: {}", e.getMessage());
        }
        currencyDTO.setCurrencyCode(entry.getKey());
        // 根據貨幣代碼找中文名稱
//        currencyDTO.setCurrencyCHName(currencyMap.getOrDefault(entry.getKey(), entry.getKey()));
        log.info("convertToTransDataDTO: " + entry.getKey());
        currencyDTO.setCurrencyCHName(CurrencyUtil.getChineseNameByCode(entry.getKey()));
        currencyDTO.setRate(entry.getValue().getRate());
        currencyDTO.setRateFloat(entry.getValue().getRateFloat());
        return currencyDTO;
    }


}
