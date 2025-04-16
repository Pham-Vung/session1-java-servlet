<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 28/03/2025
  Time: 09:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="./dist/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=Roboto+Condensed:ital,wght@0,100..900;1,100..900&display=swap"
          rel="stylesheet"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.2/css/all.min.css"/>
    <%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"--%>
    <%--          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">--%>
</head>
<body>
<div class="container">
    <header>
        <div class="topbar">
            <ul class="left-topbar">
                <li><a href="#">về coolmate</a></li>
                <li><a href="#">84rising*</a></li>
                <li><a href="#">coolxprint</a></li>
            </ul>
            <ul class="right-topbar">
                <li><a href="#">CoolClub <i class="fa-solid fa-star"></i></a></li>
                <li><a href="#">Blog</a></li>
                <li><a href="#">Trung tâm CSKH</a></li>
                <li><a href="#">Đăng nhập</a></li>
            </ul>
        </div>
        <div class="header-menu">
            <div class="logo">
                <a href="#"><img src="https://www.coolmate.me/images/logo-coolmate-new-v2.png" alt="COOLMATE_LOGO"/></a>
            </div>
            <div class="header-menu-nav-active">
                <div class="menu-item"><a href="#">nam</a></div>
                <div class="menu-item"><a href="#">nữ</a></div>
                <div class="menu-item"><a href="#">thể thao</a></div>
                <div class="menu-item"><a href="#">care&share</a></div>
            </div>
            <div class="header-actions">
                <div class="header-actions-search">
                    <input type="text" placeholder="Tìm kiếm sản phẩm..."/>
                    <i class="fa-solid fa-search search-btn"></i>
                </div>
                <div class="header-actions-account">
                    <a href="#" title="Tài khoản"><i class="fa-solid fa-user"></i></a>
                </div>
                <div class="header-actions-cart">
                    <a href="#" title="Giỏ hàng"><i class="fa-solid fa-bag-shopping"></i><span
                            class="cart-count">0</span></a>
                </div>
            </div>
        </div>
    </header>

    <div class="topbar-promotion">
        <div class="topbar-promotion-content">
            <a href="#">Tặng 50k CoolCash + Voucher 15% cho thành viên CoolClub mới! <span>Tham gia</span><i
                    class="fa-solid fa-arrow-right"></i></a>
        </div>
    </div>
    <main>
        <div class="left-main">
            <div class="pannel suitable-type">
                <div class="title" onclick="toggleList(this)">
                    <h3>Phù hợp với</h3><i class="fa-solid fa-chevron-down"></i>
                </div>
                <ul class="pannel-content suitable-list search-condition">
                    <li><input type="radio" id="home" name="suitable"/><label for="home">Mặc ở nhà</label></li>
                    <li><input type="radio" id="daily" name="suitable"/><label for="daily">Mặc hằng ngày</label></li>
                    <li><input type="radio" id="sport" name="suitable"/><label for="sport">Thể thao</label></li>
                </ul>
            </div>
            <div class="pannel size-type">
                <div class="title" onclick="toggleList(this)">
                    <h3>Kích cỡ</h3><i class="fa-solid fa-chevron-down"></i>
                </div>
                <ul class="pannel-content size-list search-condition">
                    <c:forEach var="sizeProduct" items="${sizes}">
                        <li>
                            <input type="checkbox" id="size-${sizeProduct.getId()}" name="size"/>
                            <label for="size-${sizeProduct.getId()}">${sizeProduct.getName()}</label>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="pannel color-type">
                <div class="title" onclick="toggleList(this)">
                    <h3>Màu sắc</h3><i class="fa-solid fa-chevron-down"></i>
                </div>
                <ul class="pannel-content color-list search-condition">
                    <c:forEach var="color" items="${colors}">
                        <c:if test="${color.getName().equals('Xanh')}">
                            <li>
                                <input type="radio" id="green" name="color"/>
                                <label for="green">Màu xanh</label>
                            </li>
                        </c:if>
                        <c:if test="${color.getName().equals('Đỏ')}">
                            <li>
                                <input type="radio" id="red" name="color"/>
                                <label for="red">Màu đỏ</label>
                            </li>
                        </c:if>
                        <c:if test="${color.getName().equals('Tím')}">
                            <li>
                                <input type="radio" id="purple" name="color">
                                <label for="purple">Màu tím</label>
                            </li>
                        </c:if>
                        <c:if test="${color.getName().equals('Vàng')}">
                            <li>
                                <input type="radio" id="yellow" name="color"/>
                                <label for="yellow">Màu vàng</label>
                            </li>
                        </c:if>
                        <c:if test="${color.getName().equals('Đen')}">
                            <li>
                                <input type="radio" id="black" name="color"/>
                                <label for="black">Màu đen</label>
                            </li>
                        </c:if>
                        <c:if test="${color.getName().equals('Trắng')}">
                            <li>
                                <input type="radio" id="white" name="color"/>
                                <label for="white">Màu trắng</label>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
            <div class="pannel material-type">
                <div class="title" onclick="toggleList(this)">
                    <h3>Chất liệu</h3><i class="fa-solid fa-chevron-down"></i>
                </div>
                <ul class="pannel-content material-list search-condition">
                    <li><input type="checkbox" id="cotton" name="material"/><label for="cotton">Cotton</label></li>
                    <li><input type="checkbox" id="polyester" name="material"/><label for="polyester">Polyester</label>
                    </li>
                </ul>
            </div>
        </div>
        <div class="right-main">
            <div class="empty"></div>
            <div class="product-grid">
                <c:forEach var="product" items="${products}">
                    <div class="product-item">
                        <div class="rating">5<i class="fa-solid fa-star"></i><span class="rating-count">(2)</span></div>
                        <div class="product-image">
                            <c:forEach var="productItemImage" items="${product.getImages()}">
                                <img class="main-img"
                                     src="uploads/${productItemImage}"
                                     alt="main-img"/>
                            </c:forEach>
                            <div class="size-in-stock">
                                <span class="size-title">Thêm nhanh vào giỏ hàng +</span>
                                <ul class="size-btns">
                                    <c:forEach var="productItemSize" items="${product.getSizes()}">
                                        <li class="sz-item">${productItemSize.getName()}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="product-info">
                            <ul class="color-in-stock">
                                <c:forEach var="productItemColor" items="${product.getColors()}">
                                    <c:if test="${productItemColor.getName().equals('Xanh')}">
                                        <li>
                                            <input type="radio" id="pr-1-green" name="pr-1-color"/>
                                            <label for="pr-1-green"
                                            >Xanh
                                            </label>
                                        </li>
                                    </c:if>
                                    <c:if test="${productItemColor.getName().equals('Đỏ')}">
                                        <li>
                                            <input type="radio" id="pr-1-red" name="pr-1-color"/>
                                            <label for="pr-1-red"
                                            >Đỏ
                                            </label>
                                        </li>
                                    </c:if>
                                    <c:if test="${productItemColor.getName().equals('Tím')}">
                                        <li>
                                            <input type="radio" id="pr-1-purple" name="pr-1-color"/>
                                            <label for="pr-1-purple"
                                            >Tím
                                            </label>
                                        </li>
                                    </c:if>
                                    <c:if test="${productItemColor.getName().equals('Vàng')}">
                                        <li>
                                            <input type="radio" id="pr-1-yellow" name="pr-1-color"/>
                                            <label for="pr-1-yellow"
                                            >Vàng
                                            </label>
                                        </li>
                                    </c:if>
                                    <c:if test="${productItemColor.getName().equals('Đen')}">
                                        <li>
                                            <input type="radio" id="pr-1-black" name="pr-1-color"/>
                                            <label for="pr-1-black"
                                            >Đen
                                            </label>
                                        </li>
                                    </c:if>
                                    <c:if test="${productItemColor.getName().equals('Trắng')}">
                                        <li>
                                            <input type="radio" id="pr-1-white" name="pr-1-color"/>
                                            <label for="pr-1-white"
                                            >Trắng
                                            </label>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                            <p class="product-name">${product.getName()}</p>
                            <a style="color: red" href="get-product-user-by-id?id=${product.getId()}">Chi tiết</a>
                            <div class="product-price-info">
                                <span class="apply-price">143.000<span>đ</span></span>
                                <span class="discount">
                  <p>-</p>
                  <p>20</p>
                  <p>%</p>
                </span>
                                <span class="raw-price">179.000<span>đ</span></span>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </main>
    <footer></footer>
</div>
<script src="./dist/js/main.js"></script>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"--%>
<%--        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"--%>
<%--        crossorigin="anonymous"></script>--%>
</body>
</html>
