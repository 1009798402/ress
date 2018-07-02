<html>
<head>
    <meta charset="utf-8">
    <title>商品详情</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-4 column">
            <table class="table table-bordered table-condensed">
                <thead>
                <tr class="success">
                    <th>订单号</th>
                    <th>订单总金额</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDto.orderId}</td>
                    <td>${orderDto.orderAmount}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered table-condensed">
                <thead>
                <tr class="success">
                    <th>商品ID</th>
                    <th>商品名称</th>
                    <th>商品价格</th>
                    <th>数量</th>
                    <th>小计</th>
                </tr>
                </thead>
                <tbody>
                <#list orderDto.orderDetails as orderDetail>
                <tr>
                    <td>${orderDetail.productId}</td>
                    <td>${orderDetail.productName}</td>
                    <td>${orderDetail.productPrice}</td>
                    <td>${orderDetail.productQuantity}</td>
                    <td>${orderDetail.productPrice*orderDetail.productQuantity}</td>
                </tr>
                </#list>
                </tbody>
            </table>
            <#if orderDto.orderStatus = 0 >
                <a href="/sell/seller/order/finish?orderId=${orderDto.orderId}">
                    <button type="button" class="btn btn-default btn-primary">完结订单</button>
                </a>
                <a href="/sell/seller/order/cancel?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-danger">
                    取消订单
                </a>
            </#if>

        </div>
    </div>
</div>
</body>
</html>



