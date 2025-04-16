<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 16/04/2025
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="py-2 px-5 mt-5 w-50 mx-auto">
    <div class="input-group mb-3">
        <label class="input-group-text">Tên</label>
        <input type="text" class="form-control" disabled value="${product.getName()}">
    </div>

    <div class="input-group mb-3">
        <label class="input-group-text">Số lượng</label>
        <input type="number" disabled value="${product.getQuantity()}" class="form-control">
    </div>

    <ul class="mb-3 list-group">
       <c:forEach var="color" items="${product.getColors()}">
           <li class="list-group-item">${color.getName()}</li>
       </c:forEach>
    </ul>

    <ul class="mb-3 list-group">
        <c:forEach var="size" items="${product.getSizes()}">
            <li class="list-group-item">${size.getName()}</li>
        </c:forEach>
    </ul>

    <div id="productImagesCarousel" class="carousel slide mb-3" data-bs-ride="carousel">
        <div class="carousel-inner" id="carouselInner">
            <c:forEach var="img" items="${product.getImages()}" varStatus="status">
                <div class="carousel-item ${status.first ? 'active' : ''}">
                    <img src="uploads/${img}" class="d-block w-100 product-image"
                         style="height: 200px; object-fit: cover">
                </div>
            </c:forEach>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#productImagesCarousel"
                data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#productImagesCarousel"
                data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
        </button>
    </div>


</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
</body>
</html>
