<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav>
    <h1>CGEcom</h1>

    <div class="nav-links">
        <c:if test="${loggedInUser != null && loggedInUser.role == 'USER'}">
            <a href="/orders" class="order-history-btn">Order History</a>
        </c:if>

        <div class="user-info">
            <c:if test="${loggedInUser != null}">
                <p>Welcome, ${loggedInUser.username}!</p>
                <a href="/logout" class="logout-btn">Sign Out</a>
            </c:if>
        </div>
    </div>
</nav>
