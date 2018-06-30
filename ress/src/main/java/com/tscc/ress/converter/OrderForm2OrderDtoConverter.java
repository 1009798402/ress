package com.tscc.ress.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tscc.ress.database.OrderDetail;
import com.tscc.ress.dto.OrderDto;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述:OrderForm转成OrderDtoConverter
 *
 * @author C
 * Date: 2018-06-30
 * Time: 21:01
 */
@Slf4j
public class OrderForm2OrderDtoConverter {

    public static OrderDto converter(OrderForm orderForm) {
        OrderDto orderDto = new OrderDto();

        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerOpenid(orderForm.getOpenid());

        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){
                    }.getType());
        } catch (Exception e) {
            log.error("【格式转换】 OrderForm2OrderDtoConverter 中 json格式转换发生错误, json {} " ,orderForm.getItems() );
            throw new SellException(ResultEnum.CONVERTER_ERROR);
        }
        orderDto.setOrderDetails(orderDetailList);

        return orderDto;
    }

    public static List<OrderDto> converter(List<OrderForm> orderFormList) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        for (OrderForm orderForm : orderFormList) {
            OrderDto orderDto = converter(orderForm);
            orderDtoList.add(orderDto);
        }
        return orderDtoList;
    }

}
