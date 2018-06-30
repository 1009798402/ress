package com.tscc.ress.database;

import com.tscc.ress.enums.OrderStatusEnum;
import com.tscc.ress.enums.PayStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:买家信息表
 *
 * @author C
 * @date 9:12 2018/6/30/030
 */
@Data
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class OrderMaster {

    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

     /** 支付状态, 默认未支付. */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间. */
    private Date createTime;

    /** 修改时间. */
    private Date updateTime;
}
