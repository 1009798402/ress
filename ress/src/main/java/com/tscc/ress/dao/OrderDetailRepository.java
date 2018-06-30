package com.tscc.ress.dao;

import com.tscc.ress.database.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 描述:订单详情表的Repository
 *
 * @author C
 * Date: 2018-06-30
 * Time: 9:49
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {

    /**
     * 根据买家Id查询对应的商品详情
     *
     * @param orderId 买家的Id
     * @return List<OrderDetail> 返回一个商品详情的List
     */
    List<OrderDetail> findByOrderId(String orderId);

    /**
     * 等于findById
     *
     * @param primaryId 主键Id
     * @return OrderDetail
     */
    OrderDetail findByDetailId(String primaryId);
}
