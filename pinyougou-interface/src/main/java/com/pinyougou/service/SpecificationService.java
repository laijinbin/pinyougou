package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Specification;

import java.util.List;

public interface SpecificationService {
//   List<Specification> findAll(Specification specification);

    PageResult<Specification> findByPage(Specification specification, Integer page, Integer rows);

    void save(Specification specification);

    void update(Specification specification);

    void delete(Long[] ids);
}
