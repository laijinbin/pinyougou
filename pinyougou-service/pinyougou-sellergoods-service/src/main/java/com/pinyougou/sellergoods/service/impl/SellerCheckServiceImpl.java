package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.SellerCheckMapper;
import com.pinyougou.pojo.Seller;
import com.pinyougou.service.SellerCheckService;
import com.sun.scenario.effect.impl.prism.PrImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceName = "com.pinyougou.service.SellerCheckService")
@Transactional
public class SellerCheckServiceImpl implements SellerCheckService {

    @Autowired
    private SellerCheckMapper sellerCheckMapper;
    @Override
    public PageResult<Seller> findByPage(Integer page, Integer rows, Seller seller) {
        PageInfo<Seller> pageInfo=null;
        pageInfo= PageHelper.startPage(page,rows).doSelectPageInfo(new ISelect() {
            @Override
            public void doSelect() {
                sellerCheckMapper.findByPage(seller);
            }
        });
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    @Override
    public void updateStatus(String sellerId, String status) {
        try {
            Seller seller=new Seller();
            seller.setStatus(status);
            seller.setSellerId(sellerId);
            sellerCheckMapper.updateByPrimaryKeySelective(seller);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
