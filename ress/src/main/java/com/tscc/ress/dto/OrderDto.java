package com.tscc.ress.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tscc.ress.database.OrderDetail;
import com.tscc.ress.utils.serializer.Date2LongSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 描述:用来传输OrderMaster+购物车数据的类
 *
 * @author C
 * Date: 2018-06-30
 * Time: 10:43
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    /** 订单号. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家电话. */
    private String  buyerPhone;

    /** 买家地址. */
    private String  buyerAddress;

    /** 买家微信openid. */
    private String  buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认未支付. */
    private Integer payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 修改时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 订单对应的商品详情. */
    List<OrderDetail> orderDetails;
}
