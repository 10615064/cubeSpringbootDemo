package com.example.cubespringbootdemo.feign;

import com.example.cubespringbootdemo.dto.CoindeskDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@Component
@FeignClient(name = "cube-coindesk",url = "url-placeholder")
public interface CubeCoindeskFeignClient {

    @GetMapping
    ResponseEntity<CoindeskDataDTO> getCoindeskData(URI uri);
}
