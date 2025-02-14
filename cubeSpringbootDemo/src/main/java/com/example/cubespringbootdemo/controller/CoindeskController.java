package com.example.cubespringbootdemo.controller;

import com.example.cubespringbootdemo.dto.CoindeskDataDTO;
import com.example.cubespringbootdemo.dto.CoindeskTransDataDTO;
import com.example.cubespringbootdemo.logic.CubeBrService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@Slf4j
@RestController
@RequestMapping("/coindesk")
public class CoindeskController {

    @Autowired
    private CubeBrService cubeBrService;

    @Operation(summary = "取得coindesk資料")
    @GetMapping("/uiGetCoindeskData")
    public ResponseEntity<CoindeskDataDTO> uiGetCoindeskData() throws Exception {
        try {
            return cubeBrService.getCoindeskData();
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }
    }

    @Operation(summary = "取得coindesk 轉換資料")
    @GetMapping("/uiGetCoindeskTransData")
    public ResponseEntity<List<CoindeskTransDataDTO>> uiGetCoindeskTransData() throws Exception {
        try {
            return cubeBrService.getCoindeskTransformData();
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }
    }
}
