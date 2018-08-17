<%@ page import="yourAd.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="yourAd.model.UserRole" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    User user = (User)request.getSession().getAttribute("user");
    List<User> users = (List<User>)request.getAttribute("users");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title><%=user.getName()%> <%=user.getSurname()%></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <!--[if ie]>
    <meta content='IE=8' http-equiv='X-UA-Compatible'/><![endif]-->
    <!-- bootstrap -->
    <link href="/resources/your_ad_style/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/resources/your_ad_style/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/resources/your_ad_style/themes/css/bootstrappage.css" rel="stylesheet"/>
    <link href="/resources/your_ad_style/css/style.css" rel="stylesheet"/>
    <link href="/resources/table_style/main.css" rel="stylesheet"/>

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
        <h4><span>All Users</span></h4>
    </section>
    <section class="main-content">

        <div class="row">
            <div class="span9" style="position: relative;left: 100px">
                <table class="container">
                    <thead>
                    <tr>
                        <th><h1>Id</h1></th>
                        <th><h1>Name</h1></th>
                        <th><h1>Surname</h1></th>
                        <th><h1>Username</h1></th>
                        <th><h1>Delete</h1></th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        for (User u : users) {
                            if(u.getRole().equals(UserRole.ADMIN)){
                                continue;
                            }
                    %>
                    <tr style="color: white">
                        <td><%=u.getId()%></td>
                        <td><%=u.getName()%></td>
                        <td><%=u.getSurname()%></td>
                        <td><%=u.getUsername()%></td>
                        <td>
                            <a href="/admin/user/delete/<%=u.getId()%>">Delete</a>
                        </td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
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