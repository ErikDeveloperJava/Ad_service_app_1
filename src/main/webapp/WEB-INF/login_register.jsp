<%@ page import="yourAd.model.Category" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
	List<Category> categories = (List<Category>) request.getAttribute("categories");
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Login or Register</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta name="description" content="">
		<!--[if ie]><meta content='IE=8' http-equiv='X-UA-Compatible'/><![endif]-->
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

					</div>
				</div>
			</div>
		</div>
		<div id="wrapper" class="container">
			<section class="navbar main-menu">
				<div class="navbar-inner main-menu">				
					<a href="/" class="logo pull-left"><img src="/resources/your_ad_style/themes/images/logo.png" class="site_logo" alt=""></a>
					<nav style="position: relative;right: 800px" id="menu" class="pull-right">
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
							<li><a href="/login-register">Login/Register</a></li>
						</ul>
					</nav>
				</div>
			</section>			
			<section class="header_text sub">
			<img class="pageBanner" src="/resources/your_ad_style/themes/images/pageBanner.png" alt="New products" >
				<h4><span>Login or Register</span></h4>
			</section>			
			<section class="main-content">				
				<div class="row">
					<div class="span5">					
						<h4 class="title"><span class="text"><strong>Login</strong> Form</span></h4>
						<%
							if(request.getAttribute("loginError") != null){
						%>
						<span style="color: red"><%=request.getAttribute("loginError")%></span>
						<%
							}
						%>
						<form action="/login" method="post">
							<fieldset>
								<div class="control-group">
									<label class="control-label">Username</label>
									<div class="controls">
										<input type="text" placeholder="Enter your username" id="username" name="username" class="input-xlarge">
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">Password</label>
									<div class="controls">
										<input type="password" placeholder="Enter your password" id="password" name="password" class="input-xlarge">
									</div>
								</div>
								<div class="control-group">
									<input tabindex="3" class="btn btn-inverse large" type="submit" value="Login">
								</div>
							</fieldset>
						</form>				
					</div>
					<div class="span7">					
						<h4 class="title"><span class="text"><strong>Register</strong> Form</span></h4>
						<form action="/register" method="post" class="form-stacked">
							<fieldset>
								<div class="control-group">
									<label class="control-label">Name</label>
									<div class="controls">
										<input type="text" placeholder="Enter your name" name="name" class="input-xlarge">
									</div>
									<%
										if(request.getAttribute("nameError") != null){
									%>
									<span style="color: red"><%=request.getAttribute("nameError")%></span>
									<%
										}
									%>
								</div>
								<div class="control-group">
									<label class="control-label">Surname</label>
									<div class="controls">
										<input type="text" placeholder="Enter your surname" name="surname" class="input-xlarge">
									</div>
									<%
										if(request.getAttribute("surnameError") != null){
									%>
									<span style="color: red"><%=request.getAttribute("surnameError")%></span>
									<%
										}
									%>
								</div>
								<div class="control-group">
									<label class="control-label">Username</label>
									<div class="controls">
										<input type="text" placeholder="Enter your username" name="username" class="input-xlarge">
									</div>
									<%
										if(request.getAttribute("usernameError") != null){
									%>
									<span style="color: red"><%=request.getAttribute("usernameError")%></span>
									<%
										}
									%>
								</div>
								<div class="control-group">
									<label class="control-label">Password</label>
									<div class="controls">
										<input type="password" placeholder="Enter your password" name="password" class="input-xlarge">
									</div>
									<%
										if(request.getAttribute("passwordError") != null){
									%>
									<span style="color: red"><%=request.getAttribute("passwordError")%></span>
									<%
										}
									%>
								</div>
								<div class="control-group">
								</div>
								<div class="actions"><input tabindex="9" class="btn btn-inverse large" type="submit" value="Create your account"></div>
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
			$(document).ready(function() {
				$('#checkout').click(function (e) {
					document.location.href = "checkout.html";
				})
			});
		</script>		
    </body>
</html>