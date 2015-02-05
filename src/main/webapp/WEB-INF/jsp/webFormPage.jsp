<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Eldorado Task2</title>
    <script src="<spring:url value="/resources/scripts/jquery-2.1.1.min.js" htmlEscape="true"/>"></script>

    <link rel="stylesheet" href="<spring:url value="/resources/dist/css/additional.css" htmlEscape="true"/>"
          type="text/css"/>
    <link rel="stylesheet" href="<spring:url value="/resources/dist/css/bootstrap.min.css" htmlEscape="true"/>"
          type="text/css"/>
    <link rel="stylesheet" href="<spring:url value="/resources/dist/css/bootstrap-theme.min.css" htmlEscape="true"/>"
          type="text/css"/>


</head>
<body>
<div class="wrapper">

    <div id="customersTable">
        <h3>Customers</h3>
        <table>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>orders</th>
            </tr>
            <c:forEach var="customer" items="${customers}">
                <c:set var="customerId" value="${customer.getId()}"/>
                <tr>
                    <td id="custId">${customerId}</td>
                    <td id="custName">${customer.getName()}</td>
                    <td>
                        <a href="javascript:void(0)"
                           onclick="document.getElementById('light${customerId}').style.display='block';">
                            <button class="badgeOrders" type="button">${customer.getOrders().size()}</button>
                        </a>

                        <div id="light${customerId}" class="white_content">
                            <table id="ordersTable">
                                <h4>Customer (id:${customerId}) orders</h4>
                                <tr>
                                    <th>id</th>
                                    <th>positions</th>
                                </tr>
                                <c:forEach var="order" items="${customer.getOrders()}">
                                    <c:set var="orderId" value="${order.getId()}"/>
                                    <tr>
                                        <td>${orderId}</td>
                                        <td>
                                            <a href="javascript:void(0)"
                                               onclick="document.getElementById('light${customerId}${orderId}').style.display='block'">
                                                <button class="badgeOrders"
                                                        type="button">${order.getPositions().size()}</button>
                                            </a>

                                            <div id="light${customerId}${orderId}" class="white_content">
                                                <table id="positionsTable">
                                                    <h4>Order (id:${orderId}) positions</h4>
                                                    <tr>
                                                        <th>id</th>
                                                        <th>price</th>
                                                        <th>count</th>
                                                    </tr>
                                                    <c:forEach var="positions" items="${order.getPositions()}">
                                                        <tr>
                                                            <td>${positions.getId()}</td>
                                                            <td>${positions.getPrice()}</td>
                                                            <td>${positions.getCount()}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                                <a href="javascript:void(0)"
                                                   onclick="document.getElementById('light${customerId}${orderId}').style.display='none';">
                                                    <p>
                                                        Close</p></a>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                            <a href="javascript:void(0)"
                               onclick="document.getElementById('light${customerId}').style.display='none';"><p>
                                Close</p></a></div>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div id="requestedData">
        <table>
            <tr>
                <td>1.Sum prices of all orders:</td>
                <td>${sumAllOrdersPrice}</td>
            </tr>
            <tr>
                <td>2.Prices sum of biggest order:</td>
                <td>${biggestOrderSum}</td>
            </tr>
            <tr>
                <td>3.Prices sum of smallest order:</td>
                <td>${smallestOrderSum}</td>
            </tr>
            <tr>
                <td>4.Amount of orders:</td>
                <td>${ordersAmount}</td>
            </tr>
            <tr>
                <td>5.Average orders prices sum:</td>
                <td>${averageOrderSumPrice}</td>
            </tr>
        </table>
    </div>

    <h4>Customers with orders price sum more than</h4>

    <form id="customersFilter" method="post">
        <table>
            <tr>
                <td>
                    <input type="number" name="n"/>
                </td>
                <td>
                    <input type="submit" name="submit" value="get customers">
                </td>
            </tr>
        </table>
    </form>
    <div id="customersOut"></div>
    <table>
        <c:forEach var="cust" items="${filtratedCustomers}">
            <tr id="outFilter">
                <td>${cust.getId()}</td>
                <td>${cust.getName()}</td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
