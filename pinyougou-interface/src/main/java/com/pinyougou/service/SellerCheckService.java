package com.pinyougou.service;

import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Seller;

public interface SellerCheckService {
    PageResult<Seller> findByPage(Integer page, Integer rows, Seller seller);

    void updateStatus(String sellerId, String status);
}
