package com.tscc.ress.dao;

import com.tscc.ress.database.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 描述:订单表的Repository
 *
 * @author C
 * Date: 2018-06-30
 * Time: 9:48
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {

    /**
     * 按照买家的Openid来查询
     *
     * @param openid 买家的Openid
     * @param page
     * @return Page<OrderMaster> 返回一个Page对象
     */
    Page<OrderMaster> findByBuyerOpenid(String openid, Pageable page);

    /**
     * 等于findById
     *
     * @param orderId 主键Id
     * @return OrderDetail
     */
    OrderMaster findByOrderId(String orderId);
}
