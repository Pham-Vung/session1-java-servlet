<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 2/15/2025
  Time: 7:53 PM
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
    <h1>Thêm sản phẩm</h1>
    <form action="add-product" method="post" enctype="multipart/form-data">
        <div class="input-group mb-3">
            <label class="input-group-text" for="product-name">Tên</label>
            <input type="text" class="form-control" name="product-name" id="product-name">
        </div>

        <div class="input-group mb-3">
            <label class="input-group-text" for="quantity">Số lượng</label>
            <input type="number" class="form-control" name="quantity" id="quantity"
                   min="1"
                   value="1"
                   step="1">
        </div>

        <div class="input-group mb-3">
            <label class="input-group-text" for="image">Ảnh</label>
            <input type="file" class="form-control" name="image" id="image" accept="image/*">
        </div>

        <button class="btn btn-success" type="submit">Thêm</button>
    </form>
</div>
</body>
</html>
