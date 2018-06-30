package com.tscc.ress.database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 描述:订单详情表
 *
 * @author C
 * Date: 2018-06-30
 * Time: 9:41
 */
@Data
@Entity
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    private String detailId;

    /** 订单id. */
    private String orderId;

    /** 商品id. */
    private String  productId;

    /** 商品名称. */
    private String  productName;

    /** 商品价格. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品小图. */
    private String  productIcon;

}
