package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.common.pojo.PageResult;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.Goods;
import com.pinyougou.pojo.Item;
import com.pinyougou.pojo.ItemCat;
import com.pinyougou.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service(interfaceName = "com.pinyougou.service.GoodsService")
@Transactional
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Override
    public void save(Goods goods) {
        try {
            goods.setAuditStatus("0");
            goodsMapper.insertSelective(goods);
            goods.getGoodsDesc().setGoodsId(goods.getId());
            goodsDescMapper.insertSelective(goods.getGoodsDesc());
            if ("1".equals(goods.getIsEnableSpec())){
                for (Item item : goods.getItems()) {
                    StringBuilder title=new StringBuilder();
                    title.append(goods.getGoodsName());
                    /* 把规格选项 JSON 字符串转化成 Map 集合 */
                    Map<String,Object> spec= JSON.parseObject(item.getSpec());
                    for (Object value : spec.values()) {
                        title.append(" "+value);
                    }
                    item.setTitle(title.toString());
                    setItemInfo(item, goods);
                 itemMapper.insertSelective(item);
                }
            }else {
                Item item = new Item();
/** 设置 SKU 商品的标题 */
                item.setTitle(goods.getGoodsName());
/** 设置 SKU 商品的价格 */
                item.setPrice(goods.getPrice());
/** 设置 SKU 商品库存数据 */
                item.setNum(9999);
/** 设置 SKU 商品启用状态 */
                item.setStatus("1");
/** 设置是否默认*/
                item.setIsDefault("1");
/** 设置规格选项 */
                item.setSpec("{}");
/** 设置 SKU 商品其它属性 */
                setItemInfo(item, goods);
                itemMapper.insertSelective(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public PageResult<Goods> findByPage(Integer page, Integer rows, Goods goods) {
        PageInfo<Map<String,Object>> pageInfo=null;
        try {
            pageInfo= PageHelper.startPage(page,rows).doSelectPageInfo(
                    new ISelect() {
                        @Override
                        public void doSelect() {
                           goodsMapper.findByPage(goods);
                        }
                    });
            for (Map<String, Object> map : pageInfo.getList()) {
                Long category3Id= (Long) map.get("category3Id");
                if (category3Id!=null && category3Id>0){
                ItemCat itemCat1=itemCatMapper.selectByPrimaryKey(map.get("category1Id"));
                ItemCat itemCat2=itemCatMapper.selectByPrimaryKey(map.get("category2Id"));
                ItemCat itemCat3=itemCatMapper.selectByPrimaryKey(map.get("category3Id"));
                map.put("category1Name",itemCat1.getName());
                map.put("category2Name",itemCat2.getName());
                map.put("category3Name",itemCat3.getName());
                }
            }
                return new PageResult(pageInfo.getTotal(),pageInfo.getList());
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private void setItemInfo(Item item, Goods goods){
        List<Map> imageList=JSON.parseArray(goods.getGoodsDesc().getItemImages(),Map.class);
        if (imageList!=null && imageList.size()>0){
            item.setImage((String) imageList.get(0).get("url"));
        }
        item.setCategoryid(goods.getCategory3Id());
        item.setCreateTime(new Date());
        item.setUpdateTime(item.getCreateTime());
        item.setGoodsId(goods.getId());
        item.setSellerId(goods.getSellerId());
        item.setCategory(itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName());
        item.setBrand(brandMapper.selectByPrimaryKey(goods.getBrandId()).getName());
        item.setSeller(sellerMapper.selectByPrimaryKey(goods.getSellerId()).getNickName());
    }
}
