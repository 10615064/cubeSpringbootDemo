package com.example.cubespringbootdemo.logic.Impl;

import com.example.cubespringbootdemo.dto.CoindeskDataDTO;
import com.example.cubespringbootdemo.dto.CoindeskTransDataDTO;
import com.example.cubespringbootdemo.dto.CurrencyDTO;
import com.example.cubespringbootdemo.dto.TimeInfoDTO;
import com.example.cubespringbootdemo.feign.CubeCoindeskFeignClient;
import com.example.cubespringbootdemo.logic.CubeBrService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CubeBrServiceImplTest {
    @Mock
    private CubeCoindeskFeignClient cubeCoindeskFeignClient;

    @InjectMocks
    private CubeBrServiceImpl cubeBrkserviceImpl;

    private CoindeskDataDTO coindeskDataDTO;

    private static final String COINDESK_API_URL = "https://kengp3.github.io/blog/coindesk.json";


    @BeforeEach
    void setUp() {
        // 模擬 API 回應數據
        coindeskDataDTO = new CoindeskDataDTO();

        // 設定時間
        TimeInfoDTO timeDTO = new TimeInfoDTO();
        timeDTO.setUpdated("Sep 2, 2024 07:07:20 UTC");
        timeDTO.setUpdatedISO("2024-09-02T07:07:20+00:00");
        timeDTO.setUpdateduk("Sep 2, 2024 at 08:07 BST");
        coindeskDataDTO.setTime(timeDTO);

        // 設定貨幣資料
        CurrencyDTO usdCurrency = new CurrencyDTO("USD", "57,756.298", 57756.2984);
        Map<String, CurrencyDTO> bpiMap = new HashMap<>();
        bpiMap.put("USD", usdCurrency);

        coindeskDataDTO.setBpi(bpiMap);
    }

    @Test
    void getCoindeskTransformData() throws Exception {
//        // 模擬 Feign Client 的行為
        when(cubeCoindeskFeignClient.getCoindeskData(new URI(COINDESK_API_URL)))
                .thenReturn(ResponseEntity.ok(coindeskDataDTO));

        // 執行測試方法
        ResponseEntity<CoindeskTransDataDTO> response = cubeBrkserviceImpl.getCoindeskTransformData();
        CoindeskTransDataDTO coindeskTransDataDTO = response.getBody();
        // 驗證結果
        assertNotNull(response);
        assertEquals(1, coindeskTransDataDTO.getCoindeskTransDTOList().size());
        assertEquals("USD", coindeskTransDataDTO.getCoindeskTransDTOList().get(0).getCurrencyCode());
        assertEquals("美元", coindeskTransDataDTO.getCoindeskTransDTOList().get(0).getCurrencyCHName());
        assertEquals("57,756.298", coindeskTransDataDTO.getCoindeskTransDTOList().get(0).getRate());
        assertEquals(57756.2984, coindeskTransDataDTO.getCoindeskTransDTOList().get(0).getRateFloat());
        assertEquals("2024/09/02 07:07:20", coindeskTransDataDTO.getTime().getUpdated());
        assertEquals("2024/09/02 07:07:20", (coindeskTransDataDTO.getTime().getUpdatedISO()));
        assertEquals("2024/09/02 08:07:00", coindeskTransDataDTO.getTime().getUpdateduk());
    }

}
