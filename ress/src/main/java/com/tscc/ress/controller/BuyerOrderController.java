package com.tscc.ress.controller;

import com.tscc.ress.converter.OrderForm2OrderDtoConverter;
import com.tscc.ress.dto.OrderDto;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.form.OrderForm;
import com.tscc.ress.service.OrderMasterServer;
import com.tscc.ress.utils.SellResult;
import com.tscc.ress.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author C
 * Date: 2018-06-30
 * Time: 20:16
 */
@Slf4j
@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    @Autowired
    private OrderMasterServer orderMasterServer;

    /**
     * 创建订单
     * @param orderForm 订单的form对象
     * @param bindingResult 表单校验
     * @return Map<orderId,String>  key"orderId": "147283992738221"
     */
    @PostMapping("/create")
    public ResultVo<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            log.error("【创建订单】 参数不正确, orderForm = {}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDto orderDto = OrderForm2OrderDtoConverter.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDto.getOrderDetails())){
            log.error("【创建订单】 购物车不能为空, orderDto = {}",orderDto);
            throw new SellException(ResultEnum.CART_NOT_EMPTY);
        }
        OrderDto result = orderMasterServer.createOrder(orderDto);
        Map<String,String> map = new HashMap<>(16);
        map.put("orderId",result.getOrderId());

        return SellResult.success(map);
    }

    /**
     * 订单列表
     * @param openid 微信的Openid
     * @param page 从第几页开始
     * @param size 每页显示几条
     * @return Page<OrderDto> 返回分页后的orderDto对象
     */
    @GetMapping("/list")
    public ResultVo<List<OrderDto>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("【查询所有订单】 openid不能为空 , openid = {}" , openid);
            throw new SellException(ResultEnum.OPENID_NOT_EMPTY);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderDto> orderDtoPage = orderMasterServer.findAll(openid, pageRequest);

        return SellResult.success(orderDtoPage.getContent());
    }

    //订单详情

    //取消订单
}
