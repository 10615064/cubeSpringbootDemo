package com.example.cubespringbootdemo.service.impl;


import com.example.cubespringbootdemo.entity.CurrencyTypeEntity;
import com.example.cubespringbootdemo.repository.CurrencyTypeRepository;
import com.example.cubespringbootdemo.service.CurrencyTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CurrencyTypeServiceImpl implements CurrencyTypeService {

    @Autowired
    private CurrencyTypeRepository currencyTypeRepository;


    @Override
    public void deleteList(List<Integer> ids) throws Exception{
        try {
            currencyTypeRepository.deleteAllById(ids);
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }

    }


    @Override
    public boolean update(CurrencyTypeEntity currencyType) throws Exception {
        try {
            Optional<CurrencyTypeEntity> currencyTypeEntityOptional = currencyTypeRepository.findById(currencyType.getId());
            if(currencyTypeEntityOptional.isPresent()){

                currencyTypeRepository.save(currencyType);
                return true;
            }else{
                return false;
            }

        }catch (Exception e) {
            log.error(e.getMessage());
            throw new Exception(e);
        }
    }

}
