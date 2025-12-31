<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Your Orders</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

    <!-- Custom Styles -->
    <link rel="stylesheet" href="/styles.css">

    <style>
        .order-card {
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 30px;
        }
        .order-header {
            background-color: #f8f9fa;
            padding: 15px;
            border-bottom: 1px solid #dee2e6;
        }
        .product-image {
            width: 80px;
            height: 80px;
            object-fit: cover;
            margin-right: 15px;
        }
        .product-details {
            flex: 1;
        }
        .order-info {
            font-size: 14px;
            color: #555;
        }
        .order-status {
            font-weight: bold;
            color: #28a745;
        }
    </style>
</head>
<body>

    <%@ include file="header.jsp" %>

    <div class="container my-5">
        <h2 class="text-center mb-4">Your Orders</h2>

        <c:if test="${not empty orders}">
            <c:forEach var="order" items="${orders}">
                <div class="card order-card">
                    <div class="order-header d-flex justify-content-between flex-wrap">
                        <div class="order-info">
                            <div><strong>Order ID:</strong> ${order.id}</div>
                            <div><strong>Order Status:</strong> <span class="order-status">${order.orderStatus}</span></div>
                        </div>
                        <div class="text-right order-info">
                            <div><strong>Total:</strong> ${order.totalPrice}</div>
                            <div><strong>Date:</strong> ${order.createdAt}</div>
                        </div>
                    </div>
                    <div class="card-body">
                        <c:forEach var="item" items="${order.orderItems}">
                            <div class="d-flex align-items-center mb-3 border-bottom pb-3">
                                <c:if test="${not empty item.product.imagePath}">
                                    <img src="${item.product.imagePath}" alt="${item.product.name}" class="product-image rounded">
                                </c:if>
                                <div class="product-details">
                                    <h5 class="mb-1">${item.product.name}</h5>
                                    <div><strong>Price:</strong> ${item.price}</div>
                                    <div><strong>Quantity:</strong> ${item.quantity}</div>
                                </div>
                            </div>
                        </c:forEach>
                        
                    
                        
                    </div>
                </div>
            </c:forEach>
            
                  <!-- Back button below orders -->
    <div class="text-center mt-4">
        <a href="/products" class="btn btn-outline-secondary">
            <i class="fas fa-arrow-left mr-2"></i> Back to Products
        </a>
    </div>
            
        </c:if>

        <c:if test="${empty orders}">
            <div class="text-center mt-5">
                <p class="lead">You have no orders yet.</p>
                <a href="/products" class="btn btn-primary">Start Shopping</a>
            </div>
        </c:if>
    </div>

    <%@ include file="footer.jsp" %>

    <!-- Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.4.4/dist/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
