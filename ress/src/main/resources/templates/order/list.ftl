<html>
    <#include "../common/head.ftl">
<body>
<div id="wrapper" class="toggled">
<#--侧边栏-->
    <#include "../common/nav.ftl">

<#--主要内容-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>订单号</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                    <#list orderDtos.getContent() as orderDto>
                    <tr>
                        <td>${orderDto.orderId}</td>
                        <td>${orderDto.buyerName}</td>
                        <td>${orderDto.buyerPhone}</td>
                        <td>${orderDto.buyerAddress}</td>
                        <td>${orderDto.orderAmount}</td>
                        <td>${orderDto.getOrderStatusEnum().msg}</td>
                        <td>${orderDto.getPayStatusEnum().msg}</td>
                        <td>${orderDto.createTime}</td>
                        <td>
                            <a href="http://sell.com/sell/seller/order/detail?orderId=${orderDto.orderId}">详情</a>
                        </td>
                        <td>
                                <#if orderDto.orderStatus = 0>
                                    <a href="http://sell.com/sell/seller/order/cancel?orderId=${orderDto.orderId}">取消</a>
                                </#if>
                        </td>
                    </tr>
                    </#list>
                        </tbody>
                    </table>
                </div>
                <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else >
                        <li><a href="/sell/seller/order/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..orderDtos.getTotalPages() as index >
                        <#if index == currentPage >
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte orderDtos.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else >
                        <li><a href="/sell/seller/order/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>