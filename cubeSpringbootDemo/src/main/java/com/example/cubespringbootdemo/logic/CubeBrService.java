package com.example.cubespringbootdemo.logic;


import com.example.cubespringbootdemo.dto.CoindeskDataDTO;
import com.example.cubespringbootdemo.dto.CoindeskTransDataDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CubeBrService {

    ResponseEntity<CoindeskDataDTO> getCoindeskData() throws Exception;

    ResponseEntity<CoindeskTransDataDTO> getCoindeskTransformData() throws Exception;
}
