package com.pinyougou.mapper;

import com.pinyougou.pojo.Brand;

import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    List<Brand> findAll(Brand brand);

}
