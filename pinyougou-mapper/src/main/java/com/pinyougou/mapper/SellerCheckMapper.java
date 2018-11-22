package com.pinyougou.mapper;

import com.pinyougou.pojo.Seller;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SellerCheckMapper extends Mapper<Seller> {
    List<Seller> findByPage(Seller seller);
}
