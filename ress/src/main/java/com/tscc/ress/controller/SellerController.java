package com.tscc.ress.controller;

import com.tscc.ress.dto.OrderDto;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 描述:卖家的controller  后台管理
 *
 * @author C
 * Date: 2018-07-02
 * Time: 13:49
 */
@Slf4j
@Controller
@RequestMapping("/seller/order")
public class SellerController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 显示订单列表
     *
     * @param page 当前页
     * @param size 每页显示条数
     * @return list.ftl 的freemarker模板
     */
    @GetMapping("/list")
    public ModelAndView showList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "5") Integer size,
                                 Map<String, Object> map) {

        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderDto> orderDtos = orderMasterService.findAll(pageRequest);
        map.put("orderDtos", orderDtos);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId 订单号
     * @return cancel.ftl 的freemarker模板
     */
    @GetMapping("/cancel")
    public ModelAndView cancelOrder(@RequestParam("orderId") String orderId,
                                    Map<String, Object> map) {

        try {
            OrderDto orderDto = orderMasterService.findOne(orderId);
            orderMasterService.cancel(orderDto);
        } catch (SellException e) {
            log.error("【商家取消订单】 发生异常 ", e);
            map.put("msg", e.getMessage());
            map.put("url", "http://sell.com/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "http://sell.com/sell/seller/order/list");
        return new ModelAndView("/common/success");
    }

    /**
     * 订单详情
     *
     * @param orderId 订单号
     * @return detail.ftl
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDto orderDto = orderMasterService.findOne(orderId);
            map.put("orderDto", orderDto);
        } catch (SellException e) {
            log.error("【查看订单详情】 发生异常 ", e);
            map.put("msg", e.getMessage());
            map.put("url", "http://sell.com/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }
        return new ModelAndView("/order/detail", map);
    }

    /**
     * 完结订单
     *
     * @param orderId 订单号
     * @return success.ftl
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDto orderDto = orderMasterService.findOne(orderId);
            orderMasterService.finish(orderDto);
        } catch (SellException e) {
            log.error("【完结订单详情】 发生异常 ", e);
            map.put("msg", e.getMessage());
            map.put("url", "http://sell.com/sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "http://sell.com/sell/seller/order/list");
        return new ModelAndView("/common/success");
    }


}
