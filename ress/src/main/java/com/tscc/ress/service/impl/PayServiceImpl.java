package com.tscc.ress.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import com.tscc.ress.dto.OrderDto;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.service.OrderMasterService;
import com.tscc.ress.service.PayService;
import com.tscc.ress.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 描述:支付server的实现类
 *
 * @author C
 * Date: 2018-07-01
 * Time: 20:55
 */
@Slf4j
@Service
public class PayServiceImpl implements PayService{

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public PayResponse create(OrderDto orderDto) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);

        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信支付】创建订单, request={}", JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】创建订单, response={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PayResponse notify(String notifyData) {
        //1. 验证签名
        //2. 支付的状态
        //3. 验证金额
        //4. 支付人(支付人==下单人)

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知, response={}", JsonUtil.toJson(payResponse));
        //查询订单
        OrderDto orderDto = orderMasterService.findOne(payResponse.getOrderId());

        //判断订单是否存在
        if (orderDto == null){
            log.error("【微信支付】异步通知 , 订单不存在 , orderId = {}",payResponse.getOrderId());
            throw new SellException(ResultEnum.MASTER_NOT_EXIST);
        }

        //判断金额是否一致(0.10 0.1也得考虑)
        if ( ! MathUtil.equals(orderDto.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("【微信支付】异步通知 , 订单支付价格与订单价格不一致 , 订单号 = {} , 微信通知金额 = {}, 系统金额 = {}",payResponse.getOrderId(),payResponse.getOrderAmount(),orderDto.getOrderAmount());
            throw new SellException(ResultEnum.ORDER_AMOUNT_NOT_EQUAL);
        }

        //修改订单状态
        orderMasterService.paid(orderDto);
        return payResponse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefundResponse refund(OrderDto orderDto) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】 request={}", JsonUtil.toJson(refundRequest));

        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】 response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }
}
