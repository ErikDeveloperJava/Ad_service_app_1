<%@ page import="yourAd.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="yourAd.model.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Category> categories = (List<Category>) request.getAttribute("categories");
    User user = (User) request.getSession().getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Login or Register</title>
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
        </div>
        <div class="span8">
            <div class="account pull-right">
                <span style="color: orangered"><%=user.getName()%> </span>
                <span style="color: orangered"><%=user.getSurname()%></span>
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
                            <li><a href="/admin/category/main/add">Add main category</a></li>
                            <li><a href="/admin/category/sub/add">Add sub category</a></li>
                        </ul>
                    </li>
                    <li><a href="/logout">logout</a></li>
                </ul>
            </nav>
        </div>
    </section>
    <section class="header_text sub">
        <img class="pageBanner" src="/resources/your_ad_style/themes/images/pageBanner.png" alt="New products">
        <h4><span>Add category</span></h4>
    </section>
    <section class="main-content">
        <div class="row">
            <div class="span5">
                <h4 class="title"><span class="text"><strong>Category</strong> Form</span></h4>
                <form action="/admin/category/add" method="post">
                    <fieldset>
                        <%
                            if(categories != null){
                        %>
                        <div class="control-group">
                            <label class="control-label">Categories</label>
                            <div class="controls">
                                <select name="categoryId">
                                    <%
                                        for (Category category : categories) {

                                    %>
                                    <option value="<%=category.getId()%>"><%=category.getName()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                        <%
                            }
                        %>
                        <div class="control-group">
                            <label class="control-label">Category name</label>
                            <div class="controls">
                                <input type="text" name="name">
                            </div>
                        </div>
                        <div class="control-group">
                            <input tabindex="3" class="btn btn-inverse large" type="submit" value="Add">
                        </div>
                    </fieldset>
                </form>
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
                <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. the Lorem Ipsum has been
                    the industry's standard dummy text ever since the you.</p>

            </div>
        </div>
    </section>
    <section id="copyright">
        <span>Copyright 2018 Y O U R _ A D .</span>
    </section>
</div>
<script src="/resources/your_ad_style/themes/js/common.js"></script>
<script>
    $(document).ready(function () {
        $('#checkout').click(function (e) {
            document.location.href = "checkout.html";
        })
    });
</script>
</body>
</html>