package com.tscc.ress.converter;

import com.tscc.ress.database.OrderMaster;
import com.tscc.ress.dto.OrderDto;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述:OrderMaster转换成OrderDto
 *
 * @author C
 * Date: 2018-06-30
 * Time: 15:46
 */
public class OrderMaster2OrderDtoConverter {

    public static OrderDto convert(OrderMaster orderMaster) {

        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        return orderDto;
    }

    public static List<OrderDto> convert(List<OrderMaster> orderMasterList) {
        //lambda   也可以用循环 调用上面的方法
        return orderMasterList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }

}
