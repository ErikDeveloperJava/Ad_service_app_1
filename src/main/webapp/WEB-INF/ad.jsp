<%@ page import="yourAd.model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="yourAd.model.Category" %>
<%@ page import="yourAd.model.Ad" %>
<%@ page import="yourAd.model.Image" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    User user = (User) request.getSession().getAttribute("user");
    List<Category> categories = (List<Category>) request.getAttribute("categories");
    Ad ad = (Ad) request.getAttribute("ad");
    List<Image> images = (List<Image>) request.getAttribute("images");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <%
        if (user != null) {
    %>
    <title><%=user.getName()%> <%=user.getSurname()%>
    </title>
    <%
    } else {
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

    <!-- global styles -->
    <link href="/resources/your_ad_style/themes/css/main.css" rel="stylesheet"/>
    <link href="/resources/your_ad_style/css/style.css" rel="stylesheet"/>
    <link href="/resources/your_ad_style/themes/css/jquery.fancybox.css" rel="stylesheet"/>

    <!-- scripts -->
    <script src="/resources/your_ad_style/themes/js/jquery-1.7.2.min.js"></script>
    <script src="/resources/your_ad_style/bootstrap/js/bootstrap.min.js"></script>
    <script src="/resources/your_ad_style/themes/js/superfish.js"></script>
    <script src="/resources/your_ad_style/themes/js/jquery.scrolltotop.js"></script>
    <script src="/resources/your_ad_style/themes/js/jquery.fancybox.js"></script>
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script src="js/respond.min.js"></script>
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
        <h4><span><%=ad.getTitle()%></span></h4>
    </section>
    <section class="main-content">
        <div class="row">
            <div class="span9">
                <div class="row">
                    <div class="span4">
                        <a href="/resources/images/<%=ad.getImgUrl()%>" class="thumbnail" data-fancybox-group="group1"
                           title="Description 1"><img alt="" src="/resources/images/<%=ad.getImgUrl()%>"></a>
                        <ul class="thumbnails small">
                            <%
                                for (Image img : images) {
                                    if(img.getUrl().equals(ad.getImgUrl())){
                                        continue;
                                    }
                            %>
                            <li class="span1">
                                <a href="/resources/images/<%=img.getUrl()%>" class="thumbnail" data-fancybox-group="group1"
                                   title="Description 2"><img src="/resources/images/<%=img.getUrl()%>" alt=""></a>
                            </li>
                            <%
                                }
                            %>
                        </ul>
                    </div>
                    <div class="span5">
                        <address>
                            <strong>Created date:</strong> <span><%=dateFormat.format(ad.getCreatedDate())%></span><br>
                            <%
                                if(user == null || user.getId() != ad.getUser().getId()){
                            %>
                            <strong>Author:</strong> <span><%=ad.getUser().getName()%> <%=ad.getUser().getSurname()%> </span><br>
                            <%
                                }
                            %>
                            <strong>Categories:</strong>
                            <span>
                                <%
                                    List<Category> categoryList = new ArrayList<>();
                                    Category category = ad.getCategory();
                                    while (category != null && category.getId() != null){
                                        categoryList.add(category);
                                        category = category.getParent();
                                    }
                                    int count = 1;
                                    for (Category c : categoryList) {
                                %>
                                <a href="/category/<%=c.getId()%>">
                                    <%=c.getName()%> <%=count != categoryList.size() ? "\\ " : " "%>
                                </a>
                                <%
                                        count++;
                                    }
                                %>
                            </span><br>
                        </address>
                        <h4><strong>Price: $<%=ad.getPrice()%></strong></h4>
                    </div>
                    <%
                        if(user != null && ad.getUser().getId() == user.getId()){
                    %>
                    <div class="span5">
                        <form action="/ad/image/upload" method="post" enctype="multipart/form-data" class="form-inline">
                            <input type="hidden" name="adId" value="<%=ad.getId()%>">
                            <label>Upload image:</label>
                            <input type="file" name="image" id="file" class="inputfile" />
                            <label for="file">Choose a image</label>
                            <button class="btn btn-inverse" type="submit">Upload</button>
                        </form>
                    </div>
                    <%
                        }
                    %>
                </div>
                <div class="row">
                    <div class="span9">
                        <ul class="nav nav-tabs" id="myTab">
                            <li class="active"><a>Description</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="tab-pane active" id="home">
                                <%=ad.getDescription()%>
                            </div>
                        </div>
                    </div>
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
<script>
    $(function () {
        $('#myTab a:first').tab('show');
        $('#myTab a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        })
    })
    $(document).ready(function () {
        $('.thumbnail').fancybox({
            openEffect: 'none',
            closeEffect: 'none'
        });

        $('#myCarousel-2').carousel({
            interval: 2500
        });
    });
</script>
</body>
</html>