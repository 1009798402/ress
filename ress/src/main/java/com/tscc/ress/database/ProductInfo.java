package com.tscc.ress.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tscc.ress.enums.ProductStatusEnum;
import com.tscc.ress.utils.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述:商品表
 *
 * @author C
 * @date 15:47 2018/6/29/029
 */
@Data
@Entity
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 商品状态,0正常1下架. */
    private Integer productStatus;

    /** 类目编号. */
    private Integer categoryType;

    /** 创建时间. */
    private Date createTime;

    /** 修改时间. */
    private Date updateTime;

    /** 商品状态枚举. */
    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }
}
