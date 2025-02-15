package com.example.cubespringbootdemo.logic.Impl;



import com.example.cubespringbootdemo.dto.*;

import com.example.cubespringbootdemo.feign.CubeCoindeskFeignClient;
import com.example.cubespringbootdemo.logic.CubeBrService;
import com.example.cubespringbootdemo.utils.CurrencyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CubeBrServiceImpl implements CubeBrService {

    @Autowired
    private CubeCoindeskFeignClient cubeCoindeskFeignClient;

    private static final String COINDESK_API_URL = "https://kengp3.github.io/blog/coindesk.json";

    private static final DateTimeFormatter TARGET_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    private static final DateTimeFormatter UPDATED_FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm:ss z", Locale.ENGLISH);

    private static final DateTimeFormatter UPDATED_UK_FORMATTER = DateTimeFormatter.ofPattern("MMM d, yyyy 'at' HH:mm z", Locale.ENGLISH);

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
    public ResponseEntity<CoindeskTransDataDTO> getCoindeskTransformData() throws Exception {
        try {

            CoindeskDataDTO response = cubeCoindeskFeignClient.getCoindeskData(new URI(COINDESK_API_URL)).getBody();

            TimeInfoDTO timeInfoDTO = response.getTime();
            TimeInfoDTO updatedTimeInfo = new TimeInfoDTO();
            updatedTimeInfo.setUpdated(formatTime(timeInfoDTO.getUpdated(), UPDATED_FORMATTER));
            updatedTimeInfo.setUpdatedISO(formatTime(timeInfoDTO.getUpdatedISO(), DateTimeFormatter.ISO_DATE_TIME));
            updatedTimeInfo.setUpdateduk(formatTime(timeInfoDTO.getUpdateduk(), UPDATED_UK_FORMATTER));



            Map<String, CurrencyDTO> bpiObject = response.getBpi();

            // 組織貨幣資料
            List<CoindeskTransDTO> transformedData = bpiObject.entrySet().stream()
                    .map(entry -> convertToTransDataDTO(entry))
                    .collect(Collectors.toList());

            // 設置回傳 DTO
            CoindeskTransDataDTO coindeskTransDataDTO = new CoindeskTransDataDTO();
            coindeskTransDataDTO.setTime(updatedTimeInfo);
            coindeskTransDataDTO.setCoindeskTransDTOList(transformedData);

            return ResponseEntity.ok(coindeskTransDataDTO);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }
    }

    private String formatTime(String time, DateTimeFormatter formatter) {
        if (ObjectUtils.isEmpty(time)) {
            return "";
        }
        return ZonedDateTime.parse(time, formatter).format(TARGET_FORMATTER);
    }

    private CoindeskTransDTO convertToTransDataDTO(Map.Entry<String, CurrencyDTO> entry) {
        CoindeskTransDTO currencyDTO = new CoindeskTransDTO();
        currencyDTO.setCurrencyCode(entry.getKey());
        log.info("convertToTransDataDTO: " + entry.getKey());
        currencyDTO.setCurrencyCHName(CurrencyUtil.getChineseNameByCode(entry.getKey()));
        currencyDTO.setRate(entry.getValue().getRate());
        currencyDTO.setRateFloat(entry.getValue().getRateFloat());
        return currencyDTO;
    }


}
