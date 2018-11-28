package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Goods;

public interface GoodsService {
    void save(Goods goods);

    PageResult findByPage(Integer page, Integer rows, Goods goods);
}
