package com.tscc.ress.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 描述:接收category提交的表单数据 校验
 *
 * @author C
 * Date: 2018-07-03
 * Time: 20:37
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    /** 类目名字. */
    @NotBlank
    private String categoryName;

    /** 类目编号. */
    @NotNull
    private Integer categoryType;

}
