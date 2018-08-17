<%@ page import="java.util.List" %>
<%@ page import="yourAd.model.Category" %>
<%@ page import="yourAd.model.User" %>
<%@ page import="yourAd.model.Ad" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Category> categories = (List<Category>) request.getAttribute("categories");
    User user = (User)request.getSession().getAttribute("user");
    int pageNumber = (int)request.getAttribute("pageNumber");
    int length = (int) request.getAttribute("length");
    List<Ad> adList = (List<Ad>)request.getAttribute("adList");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%
        if(user != null){
    %>
    <title><%=user.getName()%> <%=user.getSurname()%></title>
    <%
        }else {
    %>
    <title>Your ad</title>
    <%
        }
    %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <!--[if ie]>
    <meta content='IE=8' http-equiv='X-UA-Compatible'/><![endif]-->
    <!-- bootstrap -->
    <link href="/resources/your_ad_style/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/your_ad_style/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/resources/your_ad_style/themes/css/bootstrappage.css" rel="stylesheet"/>
    <link href="/resources/your_ad_style/css/style.css" rel="stylesheet"/>

    <!-- global styles -->
    <link href="/resources/your_ad_style/themes/css/flexslider.css" rel="stylesheet"/>
    <link href="/resources/your_ad_style/themes/css/main.css" rel="stylesheet"/>

    <!-- scripts -->
    <script src="/resources/your_ad_style/themes/js/jquery-1.7.2.min.js"></script>
    <script src="/resources/your_ad_style/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resources/your_ad_style/themes/js/superfish.js"></script>
    <script src="/resources/your_ad_style/themes/js/jquery.scrolltotop.js"></script>
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script src="/resources/your_ad_style/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div id="top-bar" class="container">
    <div class="row">
        <div class="span4">
            <form action="/search" method="post" role="search" class="search-form">
                <input type="submit" value="" class="search-submit">
                <input type="search" name="title" class="search-text" placeholder="Search..." autocomplete="off">
            </form>
        </div>
        <div class="span8">
            <div class="account pull-right">
                <%
                    if(user != null){
                %>
                <span style="color: orangered"><%=user.getName()%> </span>
                <span style="color: orangered"><%=user.getSurname()%></span>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>
<div id="wrapper" class="container">
    <section class="navbar main-menu">
        <div class="navbar-inner main-menu">
            <a href="/" class="logo pull-left"><img src="/resources/your_ad_style/themes/images/logo.png"
                                                    class="site_logo" alt=""></a>
            <nav style="position: relative;right: 700px" id="menu" class="pull-right">
                <ul>
                    <li><a style="cursor: pointer">Categories</a>
                        <ul>
                            <%
                                for (Category parent : categories) {
                            %>
                            <li><a href="/category/<%=parent.getId()%>"><%=parent.getName()%></a>
                                <%
                                    if(parent.getChildrenList() != null && parent.getChildrenList().size() > 0){
                                %>
                                <ul>
                                    <%
                                        for (Category child : parent.getChildrenList()) {
                                    %>
                                    <li><a href="/category/<%=child.getId()%>"><%=child.getName()%></a>
                                        <%
                                            if(child.getChildrenList() != null && child.getChildrenList().size() > 0){
                                        %>
                                        <ul>
                                            <%
                                                for (Category child2 : child.getChildrenList()) {
                                            %>
                                            <li><a href="/category/<%=child2.getId()%>"><%=child2.getName()%></a>
                                            <%
                                                }
                                            %>
                                        </ul>
                                        <%
                                            }
                                        %>
                                    <%
                                        }
                                    %>
                                </ul>

                                <%
                                    }
                                %>
                            <%
                                }
                            %>
                        </ul>
                    </li>
                    <%
                        if(user != null){
                    %>
                    <li><a href="/user/ads">My ads</a></li>
                    <li><a href="/ad/add">Add new ad</a></li>
                    <li><a href="/logout">logout</a></li>
                    <%
                        }else {
                    %>
                    <li><a href="/login-register">Login/Register</a></li>
                    <%
                        }
                    %>
                </ul>
            </nav>
        </div>
    </section>
    <section class="header_text sub">
        <img class="pageBanner" src="/resources/your_ad_style/themes/images/pageBanner.png" alt="New products">
        <h4><span><%=request.getAttribute("header")%></span></h4>
    </section>
    <section class="main-content">

        <div class="row">
            <div class="span9" style="position: relative;left: 100px">
                <ul class="thumbnails listing-products">
                    <%
                        for (Ad ad : adList) {

                    %>
                    <li class="span3">
                        <div class="product-box">
                            <span class="sale_tag"></span>
                            <a href="/ad/one/<%=ad.getId()%>">
                                <img width="200px" height="200px" alt="" src="/resources/images/<%=ad.getImgUrl()%>">
                            </a>
                            <br/>
                            <a href="/ad/one/<%=ad.getId()%>" class="title"><%=ad.getTitle()%></a><br/>
                            <p class="price">$<%=ad.getPrice()%></p>
                        </div>
                    </li>
                    <%
                        }
                    %>
                </ul>
                <hr>
                <div class="pagination pagination-small pagination-centered">
                    <ul>
                        <%
                            if(pageNumber == 0){
                        %>
                        <li><a>Prev</a></li>
                        <%
                            }else {
                        %>
                        <li><a href="/?page=<%=pageNumber-1%>">Prev</a></li>
                        <%
                            }
                        %>
                        <%
                            for (int i = 0; i < length; i++) {
                                if(i == pageNumber){
                        %>
                        <li class="active"><a><%=pageNumber+1%></a></li>
                        <%
                            }else {
                        %>
                        <li><a href="/?page=<%=i%>"><%=i + 1%></a></li>
                        <%
                                }
                            }
                            if(pageNumber == length-1){
                        %>
                        <li><a>Next</a></li>
                        <%
                            }else {
                        %>
                        <li><a href="/?page=<%=pageNumber+1%>">Next</a></li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>
    </section>
    <section id="footer-bar">
        <div class="row">
            <div class="span3">

            </div>
            <div class="span4">
            </div>
            <div class="span5">
                <p class="logo"><img src="/resources/your_ad_style/themes/images/logo.png" class="site_logo" alt=""></p>
                <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. the  Lorem Ipsum has been the industry's standard dummy text ever since the you.</p>

            </div>
        </div>
    </section>
    <section id="copyright">
        <span>Copyright 2018 Y O U R _ A D .</span>
    </section>
</div>
<script src="/resources/your_ad_style/themes/js/common.js"></script>
</body>
</html>