package com.pinyougou.manager.controller;

import com.pinyougou.pojo.Brand;
import com.pinyougou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.util.List;

@RestController
public class BrandController {

    @Reference(timeout = 10000)
    private BrandService brandService;

    @GetMapping("/brand/findAll")
    public List<Brand> findAll(){
    List<Brand> list = brandService.findAll();
    return list;

}

}
