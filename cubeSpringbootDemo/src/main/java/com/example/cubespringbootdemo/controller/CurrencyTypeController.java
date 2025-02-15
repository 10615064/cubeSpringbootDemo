package com.example.cubespringbootdemo.controller;



import com.alibaba.fastjson.JSON;
import com.example.cubespringbootdemo.entity.CurrencyTypeEntity;
import com.example.cubespringbootdemo.model.currencytype.CurrencyTypeCrForm;
import com.example.cubespringbootdemo.model.currencytype.CurrencyTypeListVO;
import com.example.cubespringbootdemo.model.currencytype.CurrencyTypePagination;
import com.example.cubespringbootdemo.model.currencytype.CurrencyTypeUpForm;
import com.example.cubespringbootdemo.repository.CurrencyTypeRepository;
import com.example.cubespringbootdemo.service.CurrencyTypeService;
import com.example.cubespringbootdemo.utils.JsonUtils;
import io.swagger.v3.oas.annotations.Operation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Slf4j
@RestController
@RequestMapping("/currencyType")
public class CurrencyTypeController {

    @Autowired
    private CurrencyTypeService currencyTypeService;

    @Autowired
    private CurrencyTypeRepository currencytypeRepository;

    @Operation(summary = "取得全部的幣別")
    @GetMapping("/uiGetAllCurrencyType")
    public ResponseEntity<List<CurrencyTypeListVO>> uiGetAllCurrencyType(){

        List<CurrencyTypeEntity> currencyTypeEntityList = currencytypeRepository.findAll();
//        List<CurrencyTypeListVO> currencyTypeEntityListVOS = JsonUtils.listTolist(currencyTypeEntityList,CurrencyTypeListVO.class);
        List<CurrencyTypeListVO> currencyTypeEntityListVOS = JSON.parseArray(JSON.toJSONString(currencyTypeEntityList), CurrencyTypeListVO.class);
        return ResponseEntity.ok(currencyTypeEntityListVOS);
    }

    @Operation(summary = "取得符合條件的幣別列表")
    @PostMapping("/uiGetFilteredCurrencyType")
    public ResponseEntity<Page<CurrencyTypeListVO>> uiGetFilteredCurrencyType(@RequestBody CurrencyTypePagination pagination) {
        log.info("Pagination params: {}", pagination);

        Pageable pageable = PageRequest.of(pagination.getPage() - 1, pagination.getSize());

        Specification<CurrencyTypeEntity> specification = (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (pagination.getId() != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), pagination.getId()));
            }


            if (pagination.getCurrencyCode() != null) {
                predicates.add(criteriaBuilder.like(root.get("currencyCode"), "%" + pagination.getCurrencyCode() + "%"));
            }

            if (pagination.getCurrencyCHName() != null) {
                predicates.add(criteriaBuilder.like(root.get("currencyCHName"), "%" + pagination.getCurrencyCHName() + "%"));
            }
            if (CollectionUtils.isEmpty(predicates)) {
                //查詢所有資料
                return criteriaBuilder.conjunction();
            }
            //建立查詢條件
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        log.info("pageable: {}", pageable);

        Page<CurrencyTypeEntity> currencyTypeEntityPage = currencytypeRepository.findAll(specification, pageable);
        log.info("currencyTypeEntityPage content: {}", currencyTypeEntityPage.getContent());


        List<CurrencyTypeListVO> currencyTypeList = JSON.parseArray(JSON.toJSONString(currencyTypeEntityPage.getContent()), CurrencyTypeListVO.class);
        log.info("currencyTypeList:{}", currencyTypeList);

        Page<CurrencyTypeListVO> currencyTypeEntityListVOS = new PageImpl<>(currencyTypeList, pageable, currencyTypeEntityPage.getTotalElements());


        return ResponseEntity.ok(currencyTypeEntityListVOS);
    }


    @Operation(summary = "刪除")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Integer id) throws Exception {
        currencytypeRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteList")
    public ResponseEntity<Void> deleteList(@RequestBody List<Integer> ids) throws Exception {
        currencyTypeService.deleteList(ids);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "單筆新增幣別主檔")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CurrencyTypeCrForm currencyTypeCrForm) throws Exception {
        try {
            if (StringUtils.isBlank(currencyTypeCrForm.getCurrencyCode())) {
                return new ResponseEntity<>("幣別代碼不可為空", HttpStatus.BAD_REQUEST);
            }

            //新增時檢查幣別代碼是否已存在
            if (StringUtils.equals(currencyTypeCrForm.getRowStatus(), "C") && currencytypeRepository.existsByCurrencyCode(currencyTypeCrForm.getCurrencyCode())) {
                return new ResponseEntity<>("幣別代碼已存在", HttpStatus.BAD_REQUEST);
            }


            CurrencyTypeEntity currencyType = JsonUtils.objectToObject(currencyTypeCrForm, CurrencyTypeEntity.class);


            currencytypeRepository.save(currencyType);

            return ResponseEntity.ok().build();
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }

    }


    @Operation(summary = "多筆新增/修改幣別主檔")
    @PostMapping("/update")
    public ResponseEntity<String> updateList(@RequestBody List<CurrencyTypeUpForm> currencyTypeUpFormList) throws Exception {
        try {
            List<CurrencyTypeEntity> currencyTypeEntityList = new ArrayList<>();
            List<String> errorMessages = new ArrayList<>();

            for (CurrencyTypeUpForm upForm : currencyTypeUpFormList) {
                //檢查幣別代碼是否為空
                if (StringUtils.isBlank(upForm.getCurrencyCode())) {
                    errorMessages.add("幣別代碼不可為空，發生於：" + upForm);
                    continue;
                }

                //檢查 CURRENCY_CODE 是否已存在
                if (StringUtils.equals(upForm.getRowStatus(), "C") && currencytypeRepository.existsByCurrencyCode(upForm.getCurrencyCode())) {
                    errorMessages.add("幣別代碼已存在，發生於：" + upForm);
                    continue;
                }

                CurrencyTypeEntity entity = new CurrencyTypeEntity();
                BeanUtils.copyProperties(upForm, entity);
                currencyTypeEntityList.add(entity);
            }


            if (!errorMessages.isEmpty()) {
                return ResponseEntity.badRequest().body(String.join("；", errorMessages));
            }

            currencytypeRepository.saveAll(currencyTypeEntityList);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }
    }
}
