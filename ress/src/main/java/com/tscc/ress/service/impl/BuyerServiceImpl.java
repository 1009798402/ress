package com.tscc.ress.service.impl;

import com.tscc.ress.dto.OrderDto;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.service.BuyerService;
import com.tscc.ress.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 描述:买家相关操作的实现类
 *
 * @author C
 * Date: 2018-07-01
 * Time: 10:31
 */
@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderMasterService orderMasterServer;
    @Override
    public OrderDto findOneOrder(String openid, String orderId) {
        return checkFindOneOrder(openid, orderId);
    }

    @Override
    public OrderDto cancelOrder(String openid,String orderId) {
        OrderDto orderDto = checkFindOneOrder(openid, orderId);
        if (orderDto == null){
            log.error("【取消订单】 订单不存在 orderId = {}",orderId);
            throw new SellException(ResultEnum.MASTER_NOT_EXIST);
        }
        return orderMasterServer.cancel(orderDto);
    }

    /**
     * 查询单个订单 并且判断openid是否相同 抽取了上面两个的重复代码
     * @param openid openid
     * @param orderId 订单号
     * @return  返回订单结果
     */
    private OrderDto checkFindOneOrder(String openid, String orderId){
        OrderDto orderDto = orderMasterServer.findOne(orderId);
        if (StringUtils.isEmpty(openid) || StringUtils.isEmpty(orderId)){
           return null;
        }
        if (!orderDto.getBuyerOpenid().equals(openid)){
            log.error("【买家查询订单】 openid不相同 买家的openid = {}",openid);
            throw new SellException(ResultEnum.OPENID_NOT_EQUAL);
        }
        return orderDto;
    }
}
