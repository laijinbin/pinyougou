package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Brand;


import java.util.List;

public interface BrandService {
//    List<Brand> findAll();

    void save(Brand brand);

    void update(Brand brand);

    PageResult<Brand> findByPage(Brand brand, Integer page, Integer rows);

    void delete(Long[] ids);
}
