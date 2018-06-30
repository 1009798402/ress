package com.tscc.ress.database;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 对应数据库product_category表
 *
 * @author C
 * @date 2016/10/31
 */
@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    /** 类目id.*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /** 类目名字.*/
    private String categoryName;

    /** 类目编号.*/
    private Integer categoryType;
}

