<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2/15/2025
  Time: 10:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
<div class="col-sm-8 py-2 px-5 offset-2 mt-5">
    <h1>Thông tin sản phẩm</h1>
    <form action="update-product-servlet" method="post" enctype="multipart/form-data">
        <div class="input-group mb-3">
            <label class="input-group-text" for="id-product">ID</label>
            <input type="text" class="form-control" name="id-product" id="id-product" value="${product.getId()}">
        </div>

        <div class="input-group mb-3">
            <label class="input-group-text" for="product-name">Tên</label>
            <input type="text" class="form-control" name="product-name" id="product-name" value="${product.getName()}">
        </div>

        <div class="input-group mb-3">
            <label class="input-group-text" for="quantity">Số lượng</label>
            <input type="number" class="form-control" name="quantity" id="quantity"
                   min="1"
                   value="${product.getQuantity()}"
                   step="1">
        </div>

        <div class="mb-3">
            <select class="form-select" name="sizes" id="sizes" multiple>
                <c:forEach var="size" items="${product.getSizes()}">
                    <option value="${size.getId()}">${size.getName()}</option>
                </c:forEach>
            </select>
        </div>

        <div class="mb-3">
            <select class="form-select" name="colors" id='colors' multiple>
                <c:forEach var="color" items="${product.getColors()}">
                    <option value="${color.getId()}">${color.getName()}</option>
                </c:forEach>
            </select>
        </div>
        <div class="input-group mb-3">
            <input type="hidden" name="isImageUpdated" id="isImageUpdated" value="false">
            <input type="file" class="form-control" name="image" id="image" accept="image/**" multiple>
        </div>
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

        <button class="btn btn-warning" type="submit">Lưu</button>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script>
    document.getElementById("image").addEventListener("change", e => {
        document.getElementById("isImageUpdated").value = "true";
        const files = e.target.files;
        const carouselInner = document.getElementById("carouselInner");

        if (files.length > 0) {
            carouselInner.innerHTML = "";

            Array.from(files).forEach((file, index) => {
                const reader = new FileReader();

                reader.onload = (event) => {
                    let newImage = `<div class="carousel-item ${index == 0 ? 'active' : ''}">
                                    <img src="${event.target.result}" class="d-block w-100 product-image">
                                </div>`;
                    carouselInner.innerHTML += newImage;
                };

                reader.readAsDataURL(file);
            });
        }
    })
</script>
</body>
</html>
