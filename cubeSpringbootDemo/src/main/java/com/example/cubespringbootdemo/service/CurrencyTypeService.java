package com.example.cubespringbootdemo.service;


import com.example.cubespringbootdemo.entity.CurrencyTypeEntity;

import java.util.List;

public interface CurrencyTypeService {

    void deleteList(List<Integer> ids)throws Exception;

    boolean update(CurrencyTypeEntity currencyType) throws Exception;


}
