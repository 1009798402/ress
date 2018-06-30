package com.tscc.ress.service.impl;

import com.tscc.ress.converter.OrderMaster2OrderDtoConverter;
import com.tscc.ress.dao.OrderDetailRepository;
import com.tscc.ress.dao.OrderMasterRepository;
import com.tscc.ress.database.OrderDetail;
import com.tscc.ress.database.OrderMaster;
import com.tscc.ress.database.ProductInfo;
import com.tscc.ress.dto.CartDto;
import com.tscc.ress.dto.OrderDto;
import com.tscc.ress.enums.OrderStatusEnum;
import com.tscc.ress.enums.PayStatusEnum;
import com.tscc.ress.enums.ResultEnum;
import com.tscc.ress.exception.SellException;
import com.tscc.ress.service.OrderMasterServer;
import com.tscc.ress.service.ProductInfoService;
import com.tscc.ress.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述:OrderMasterServer的实现类
 *
 * @author C
 * Date: 2018-06-30
 * Time: 11:03
 */
@Slf4j
@Service
public class OrderMasterServerImpl implements OrderMasterServer {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDto createOrder(OrderDto orderDto) {

        //订单总价
        BigDecimal orderAmout = new BigDecimal(0);
        //订单号
        String orderId = KeyUtil.getUniqueKey();
        //1. 订单详情List
        List<OrderDetail> orderDetailList = orderDto.getOrderDetails();
        for (OrderDetail orderDetail : orderDetailList) {
            //2. 根据订单详情中商品ID查询到该商品
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //3. 计算订单总价
            orderAmout = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmout);
            //4. 填充订单详情
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            //5. 把订单详情写入订单详情表
            orderDetailRepository.save(orderDetail);
        }
        //6. 创建一个买家对象
        OrderMaster orderMaster = new OrderMaster();
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto, orderMaster);
        //7. copy完修改并填充字段
        orderMaster.setOrderAmount(orderAmout);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        //8. 写入master表
        orderMasterRepository.save(orderMaster);
        //9. 扣库存(也可以在上面for循环中每次往cartDtoList中添加CartDto)
        List<CartDto> cartDtoList = orderDetailList.stream().map(e ->
                new CartDto(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decreaseProductDescription(cartDtoList);
        //10. 返回OrderDto
        return orderDto;
    }

    @Override
    public OrderDto findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepository.findByOrderId(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.MASTER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.DETAIL_NOT_EXIST);
        }
        //创建一个返回结果orderDto  填充并返回
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetails(orderDetailList);

        return orderDto;
    }

    @Override
    public Page<OrderDto> findAll(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasters = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        //转换成List<OrderDto>
        List<OrderDto> orderDtoList = OrderMaster2OrderDtoConverter.convert(orderMasters.getContent());
        //再转换成Page<OrderDto>
        return new PageImpl<>(orderDtoList, pageable, orderMasters.getTotalElements());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDto cancel(OrderDto orderDto) {
        //判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确 , orderId = {}, orderStatus = {}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster master = orderMasterRepository.save(orderMaster);
        if (master == null){
            log.error("【取消订单】 订单更新失败 , orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返还库存
        if(CollectionUtils.isEmpty(orderDto.getOrderDetails())){
            log.error("【取消订单】 没有订单详情 , orderDto = {}", orderDto);
            throw new SellException(ResultEnum.DETAIL_NOT_EXIST);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetails().stream()
                .map(
                e -> new CartDto(e.getProductId() , e.getProductQuantity())
        ).collect(Collectors.toList());

        productInfoService.increaseProductDescription(cartDtoList);
        //如果已付款,退款
        if (orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        if (!OrderStatusEnum.NEW.getCode().equals(orderDto.getOrderStatus())) {
            log.error("【完结订单】 订单状态不正确 , orderId = {}, orderStatus = {}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改dto订单状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        //把dto内容copy到master对象并且修改数据库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("【完结订单】 订单更新失败 , orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDto paid(OrderDto orderDto) {
        //判断订单状态
        if ( ! OrderStatusEnum.NEW.getCode().equals(orderDto.getOrderStatus())){
            log.error("【完成支付】 订单状态不正确 , orderId = {}, orderStatus = {}", orderDto.getOrderId(), orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断订单支付状态
        if (! PayStatusEnum.WAIT.getCode().equals(orderDto.getPayStatus())){
            log.error("【完成支付】 支付状态不正确 , orderDto = {}", orderDto);
            throw new SellException(ResultEnum.PAY_STATUS_ERROR);
        }
        //修改dto订单状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        //写入数据库
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("【完结订单】 订单更新失败 , orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDto;
    }
}
