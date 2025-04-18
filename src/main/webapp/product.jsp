<%--Created by IntelliJ IDEA.--%>
<%--  User: DELL--%>
<%--  Date: 2/10/2025--%>
<%--  Time: 9:53 AM--%>
<%--  To change this template use File | Settings | File Templates.--%>
<%--&ndash;%&gt;--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"
          integrity="sha512-Evv84Mr4kqVGRNSgIGL/F/aIDqQb7xQ2vcrdIwxfjThSH8CSR7PBEakCr51Ck+w+/U6swU2Im1vVX0SVk9ABhg=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>--%>
</head>
<body>
<div class="container mt-5">
    <div class="row mb-2 align-items-center">
        <div class="col col-2">
            <button type="button" data-bs-toggle="modal" data-bs-target="#exampleModal" class="btn btn-secondary">
                Thêm sản phẩm
            </button>
        </div>

        <%--        add product--%>
        <div class="py-2 px-5 modal fade" id="exampleModal" tabindex="-1"
             aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Thêm sản phẩm</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <form action="add-product" method="post" enctype="multipart/form-data">
                        <div class="modal-body">
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
                                <input type="file" multiple class="form-control mb-2" name="image" id="image"
                                       accept="image/**">
                            </div>


                            <select class="form-select" name="sizes" id="sizes" multiple
                                    data-live-search="true">
                                <c:forEach var="size" items="${sizes}">
                                    <option value="${size.getId()}">${size.getName()}</option>
                                </c:forEach>
                            </select>

                            <select class="form-select" name="colors" id='colors' multiple
                                    data-live-search="true">
                                <c:forEach var="color" items="${colors}">
                                    <option value="${color.getId()}">${color.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                            <button type="submit" class="btn btn-primary">Lưu</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%--        end add product--%>

        <div class="toast-container position-fixed top-0 end-0 p-3" id="successToast">
            <div id="successToastInner" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header bg-success text-white">
                    <strong class="me-auto">Thành công</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    Thêm sản phẩm thành công
                </div>
            </div>
        </div>

        <div class="toast-container position-fixed top-0 end-0 p-3" id="errorToast">
            <div id="errorToastInner" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header bg-danger text-white">
                    <strong class="me-auto">Lỗi</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    Lỗi khi thêm sản phẩm
                </div>
            </div>
        </div>

        <div class="col col-4">
            <select class="form-select" id="sortSelect" onchange="fetchSortProducts()">
                <option value="" ${param.sort == null ? "selected" : ""}>Sắp xếp theo</option>
                <option value="byName"  ${param.sort == "byName" ? "selected" : ""}>Theo tên</option>
                <option value="byQuantity"
                ${param.sort == "byQuantity" ? "selected" : ""}
                >Số lượng tăng dần
                </option>
            </select>
        </div>
        <div class="col col-6">
            <%--            <form action="product-servlet" method="get" class="input-group">--%>
            <input
                    type="text"
                    name="keyword"
                    id="keyword"
                    placeholder="Tìm theo tên..."
                    class="form-control"
                    onkeyup="fetchSearchProducts()"
            >
        </div>
    </div>
    <div class="product-list">
        <table class="table table-bordered table-hover text-center align-middle">
            <thead>
            <tr>
                <th>ID</th>
                <th>Tên sản phẩm</th>
                <th>Số lượng</th>
                <th colspan="2">Khác</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.getId()}</td>
                    <td>${product.getName()}</td>
                    <td>${product.getQuantity()}</td>
                    <td>
                        <a href="get-product-by-id?id=${product.getId()}" class="btn btn-warning">
                            <i class="fa-regular fa-pen-to-square"></i>
                        </a>
                    </td>
                    <td>
                        <a href="delete-product-servlet?id=${product.getId()}"
                           class="btn btn-danger">
                            <i class="fa-solid fa-trash"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>
<script>
    const fetchSortProducts = () => {
        let select = document.getElementById("sortSelect").value;
        fetch("sort-product-servlet?sort=" + encodeURIComponent(select))
            .then(res => res.json())
            .then(result => {
                renderProducts(result);
            })
            .catch(err => console.error(err));
    }

    const renderProducts = (data) => {
        let html = "";
        if (data.length === 0) {
            html = "<p>Không có sản phẩm nào</p>"
            document.querySelector(".product-list").innerHTML = html;
        } else {
            data.forEach((p) => {
                let data = `<tr>
                            <td>` + p.id + `</td>
                            <td>` + p.name + `</td>
                            <td>` + p.quantity + `</td>
                            <td>
                            <a href="get-product-by-id?id=` + p.id + `" class="btn btn-warning">
                            <i class="fa-regular fa-pen-to-square"></i></a>
                            </td>
                            <td>
                            <a href="delete-product-servlet?id=` + p.id + `" class="btn btn-danger">
                            <i class="fa-solid fa-trash"></i>
                            </a>
                            </td>
                            </tr>`
                html += data;
            });
            document.querySelector("tbody").innerHTML = html;
        }
    }

    const fetchSearchProducts = () => {
        var keyword = document.getElementById("keyword").value;
        fetch("search-servlet?keyword=" + encodeURIComponent(keyword))
            .then(res => res.json())
            .then(result => {
                // console.log(result);
                renderProducts(result);
            })
            .catch(err => console.error(err));
    }

    var flag = "${param.error}";

    if (flag && flag.trim() !== "") {
        var isError = flag === "true";
        if (!isError) {
            var successToast = document.getElementById("successToastInner");
            new bootstrap.Toast(successToast).show();
        } else {
            var errorToast = document.getElementById("errorToastInner");
            new bootstrap.Toast(errorToast).show();
        }
    }

    document.getElementById("image").addEventListener("change", e => {
        const file = e.target.files[0];

        if (file) {
            const reader = new FileReader();

            reader.onload = (event) => {
                const imagePreview = document.getElementById("imagePreview");
                imagePreview.src = event.target.result;
            }

            reader.readAsDataURL(file);
        }
    })
</script>
</body>
</html>
