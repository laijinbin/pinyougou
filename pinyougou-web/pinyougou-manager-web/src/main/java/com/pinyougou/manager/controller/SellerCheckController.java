package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerCheckService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/sellerCheck")
public class SellerCheckController {

    @Reference(timeout = 10000)
    private SellerCheckService sellerCheckService;
    @GetMapping("/findByPage")
    public PageResult<Seller> findByPage(Integer page,Integer rows,Seller seller){
        try {
            if (seller!=null && StringUtils.isNoneBlank(seller.getName())){
                seller.setName(new String(seller.getName().getBytes("ISO8859-1"),
                        "utf-8"));
            }
            if (seller!=null && StringUtils.isNoneBlank(seller.getNickName())){
                seller.setNickName(new String(seller.getNickName().getBytes("ISO8859-1"),
                        "utf-8"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return sellerCheckService.findByPage(page,rows,seller);

    }
    @GetMapping("/updateStatus")
    public boolean updateStatus(String sellerId,String status) {
        try {
            sellerCheckService.updateStatus(sellerId, status);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
