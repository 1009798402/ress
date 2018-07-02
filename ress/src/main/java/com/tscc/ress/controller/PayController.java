package com.tscc.ress.controller;

import com.lly835.bestpay.model.PayResponse;
import com.tscc.ress.dto.OrderDto;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.service.OrderMasterService;
import com.tscc.ress.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述:支付Controller
 *
 * @author C
 * Date: 2018-07-01
 * Time: 20:46
 */
@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderMasterService orderMasterServer;
    @Autowired
    private PayService payService;

    /**
     * 生成订单
     * @param orderId 订单号
     * @param returnUrl 最终重定向的路径(在模板中跳转)
     * @return 返回pay.html 的freemarker模板
     */
    @GetMapping(value = "create")
    public ModelAndView create (@RequestParam("orderId") String orderId,
                                @RequestParam("returnUrl") String returnUrl,
                                Map<String,Object> map){
        //1.查询订单
        OrderDto orderDto = orderMasterServer.findOne(orderId);
        if (orderDto == null){
            log.error("【创建支付订单】 没有查询到该订单 orderId{}=",orderId);
            throw new SellException(ResultEnum.MASTER_NOT_EXIST);
        }
        //2.创建订单
        PayResponse payResponse = payService.create(orderDto);

        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);

        return new ModelAndView("pay/create",map);
    }

    /**
     * 完结订单
     * 接收微信异步支付成功通知  修改状态码
     * @param notifyData 微信异步通知的内容
     * @return 返回pay.html 的freemarker模板 ftl
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestParam ("notifyData") String notifyData){

        //修改订单状态
        payService.notify(notifyData);
        //返回给微信处理结果  使用支付成功模板
        return new ModelAndView("pay/success");
    }
}
