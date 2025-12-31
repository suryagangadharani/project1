<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User List - Codegnan Ecom</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="/styles.css">
</head>
<body>

<div class="container mt-5">
    <!-- Page Title -->
    <div class="d-flex justify-content-between align-items-center mb-4 flex-wrap">
        <h1 class="mb-0">User List</h1>
        <div class="mt-3 mt-md-0">
            <a href="/admin/dashboard" class="btn btn-secondary mr-2">Back to Dashboard</a>
            <a href="/users/create" class="btn btn-primary">Create New User</a>
        </div>
    </div>

    <!-- User Table -->
    <div class="table-responsive">
        <table class="table table-striped table-bordered text-center">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Role</th>
                    <th>Phone Number</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.role}</td>
                        <td>${user.phone_number}</td>
                        <td>${user.email}</td>
                        <td>
                            <a href="/users/edit/${user.id}" class="btn btn-warning btn-sm mr-2">Edit</a>
                            <a href="/users/delete/${user.id}" class="btn btn-danger btn-sm">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<%@ include file="footer.jsp" %>
</body>
</html>
