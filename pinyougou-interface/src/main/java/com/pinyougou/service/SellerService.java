package com.pinyougou.service;

import com.pinyougou.pojo.Seller;

public interface SellerService {
    void save(Seller seller);


    Seller findOne(String id);
}
