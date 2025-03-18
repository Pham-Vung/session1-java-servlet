<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 18/03/2025
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="https://www.coolmate.me/images/logo-coolmate-new-v2.png" class="w-50 h-50">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Quần áo</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Giầy dép</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Đồ chơi</a>
                </li>
            </ul>
            <form class="d-flex" role="search">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>
<div class="container mt-4">
    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-md-4 col-sm-6 mb-4">
             <div class="card h-100">
                 <div id="carousel${product.id}" class="carousel slide" data-bs-ride="carousel">
                     <div class="carousel-inner">
                         <c:forEach var="image" items="${product.getImages()}" varStatus="status">
                             <div class="carousel-item ${status.first ? 'active': ''}">
                                 <img src="uploads/${image}" class="d-block w-100" style="height: 200px; object-fit: cover">
                             </div>
                         </c:forEach>
                     </div>
                     <button class="carousel-control-prev" type="button" data-bs-target="#carousel${product.id}" data-bs-slide="prev">
                         <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                     </button>
                     <button class="carousel-control-next" type="button" data-bs-target="#carousel${product.id}" data-bs-slide="next">
                         <span class="carousel-control-next-icon" aria-hidden="true"></span>
                     </button>
                 </div>
                 <div class="card-body">
                     <h5 class="card-title">${product.getName()}</h5>
                     <p class="card-text">${product.getQuantity()}</p>
                     <button class="btn btn-primary">Mua ngay</button>
                 </div>
             </div>
            </div>
        </c:forEach>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
