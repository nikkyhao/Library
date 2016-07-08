<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html lang="en">
<head>
<%@page import="java.sql.ResultSet" import="com.entity.*"
	import="java.sql.SQLException" import="java.sql.Statement"
	import="com.test.LibConnection" import="java.sql.Date"%>
<%!
	
	ResultSet book = null;
	String return_date;
	String state;
	String bookid;
	String cardid;
	%>
<%
	book = (ResultSet) session.getAttribute("SearchBack");
	book.beforeFirst();
%>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>图书馆管理系统</title>

<meta name="description"
	content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<link href="assets//css/base.css" type="text/css" rel="stylesheet" />

<style type="text/css">
/* search */
.search {
	border: 2px solid #f58400;
	height: 35px;
	margin: 40px auto 0 auto;
	width: 525px;
}

.search select {
	display: none;
}

.search .select_box {
	font-size: 12px;
	color: #999999;
	width: 100px;
	line-height: 35px;
	float: left;
	position: relative;
}

.search .select_showbox {
	height: 35px;
	background: url(images/search_ico.png) no-repeat 80px center;
	text-indent: 1.5em;
}

.search .select_showbox.active {
	background: url(images/search_ico_hover.png) no-repeat 80px center;
}

.search .select_option {
	border: 2px solid #f58400;
	border-top: none;
	display: none;
	left: -2px;
	top: 35px;
	position: absolute;
	z-index: 99;
	background: #fff;
}

.search .select_option li {
	text-indent: 1.5em;
	width: 90px;
	cursor: pointer;
}

.search .select_option li.selected {
	background-color: #F3F3F3;
	color: #999;
}

.search .select_option li.hover {
	background: #BEBEBE;
	color: #fff;
}

.search input.inp_srh, .search input.btn_srh {
	border: none;
	background: none;
	height: 35px;
	line-height: 35px;
	float: left
}

.search input.inp_srh {
	outline: none;
	width: 365px;
}

.search input.btn_srh {
	background: #f58400;
	color: #FFF;
	font-family: "微软雅黑";
	font-size: 15px;
	width: 60px;
}
</style>
<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="assets//css/bootstrap.min.css" />
<link rel="stylesheet"
	href="assets//font-awesome/4.2.0/css/font-awesome.min.css" />

<!-- page specific plugin styles -->
<link rel="stylesheet" href="assets//css/jquery-ui.min.css" />
<link rel="stylesheet" href="assets//css/datepicker.min.css" />
<link rel="stylesheet" href="assets//css/ui.jqgrid.min.css" />

<!-- text fonts -->
<link rel="stylesheet" href="assets//fonts/fonts.googleapis.com.css" />

<!-- ace styles -->
<link rel="stylesheet" href="assets//css/ace.min.css"
	class="ace-main-stylesheet" id="main-ace-style" />

<!--[if lte IE 9]>
			<link rel="stylesheet" href="assets//css/ace-part2.min.css" class="ace-main-stylesheet" />
		<![endif]-->

<!--[if lte IE 9]>
		  <link rel="stylesheet" href="assets//css/ace-ie.min.css" />
		<![endif]-->

<!-- inline styles related to this page -->

<!-- ace settings handler -->
<script src="assets//js/ace-extra.min.js"></script>

<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

<!--[if lte IE 8]>
		<script src="assets//js/html5shiv.min.js"></script>
		<script src="assets//js/respond.min.js"></script>
		<![endif]-->
</head>

<body class="no-skin">
	<div id="navbar" class="navbar navbar-default">
		<script type="text/javascript">
			try {
				ace.settings.check('navbar', 'fixed')
			} catch (e) {
			}
		</script>

		<div class="navbar-container" id="navbar-container">
			<button type="button" class="navbar-toggle menu-toggler pull-left"
				id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>

				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>

			<div class="navbar-header pull-left">
				<a href="index.jsp" class="navbar-brand"> <small> <i
						class="fa fa-leaf"></i> 图书馆管理系统
				</small>
				</a>
			</div>

			<div class="navbar-buttons navbar-header pull-right"
				role="navigation">
				<ul class="nav ace-nav">
					<li class="grey"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#"> <i
							class="ace-icon fa fa-tasks"></i> <span class="badge badge-grey">4</span>
					</a>

						<ul
							class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
							<li class="dropdown-header"><i class="ace-icon fa fa-check"></i>
								4 Tasks to complete</li>

							<li class="dropdown-content">
								<ul class="dropdown-menu dropdown-navbar">
									<li><a href="#">
											<div class="clearfix">
												<span class="pull-left">Software Update</span> <span
													class="pull-right">65%</span>
											</div>

											<div class="progress progress-mini">
												<div style="width: 65%" class="progress-bar"></div>
											</div>
									</a></li>

									<li><a href="#">
											<div class="clearfix">
												<span class="pull-left">Hardware Upgrade</span> <span
													class="pull-right">35%</span>
											</div>

											<div class="progress progress-mini">
												<div style="width: 35%"
													class="progress-bar progress-bar-danger"></div>
											</div>
									</a></li>

									<li><a href="#">
											<div class="clearfix">
												<span class="pull-left">Unit Testing</span> <span
													class="pull-right">15%</span>
											</div>

											<div class="progress progress-mini">
												<div style="width: 15%"
													class="progress-bar progress-bar-warning"></div>
											</div>
									</a></li>

									<li><a href="#">
											<div class="clearfix">
												<span class="pull-left">Bug Fixes</span> <span
													class="pull-right">90%</span>
											</div>

											<div class="progress progress-mini progress-striped active">
												<div style="width: 90%"
													class="progress-bar progress-bar-success"></div>
											</div>
									</a></li>
								</ul>
							</li>

							<li class="dropdown-footer"><a href="#"> See tasks with
									details <i class="ace-icon fa fa-arrow-right"></i>
							</a></li>
						</ul></li>

					<li class="purple"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#"> <i
							class="ace-icon fa fa-bell icon-animated-bell"></i> <span
							class="badge badge-important">8</span>
					</a>

						<ul
							class="dropdown-menu-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
							<li class="dropdown-header"><i
								class="ace-icon fa fa-exclamation-triangle"></i> 8 Notifications
							</li>

							<li class="dropdown-content">
								<ul class="dropdown-menu dropdown-navbar navbar-pink">
									<li><a href="#">
											<div class="clearfix">
												<span class="pull-left"> <i
													class="btn btn-xs no-hover btn-pink fa fa-comment"></i> New
													Comments
												</span> <span class="pull-right badge badge-info">+12</span>
											</div>
									</a></li>

									<li><a href="#"> <i
											class="btn btn-xs btn-primary fa fa-user"></i> Bob just
											signed up as an editor ...
									</a></li>

									<li><a href="#">
											<div class="clearfix">
												<span class="pull-left"> <i
													class="btn btn-xs no-hover btn-success fa fa-shopping-cart"></i>
													New Orders
												</span> <span class="pull-right badge badge-success">+8</span>
											</div>
									</a></li>

									<li><a href="#">
											<div class="clearfix">
												<span class="pull-left"> <i
													class="btn btn-xs no-hover btn-info fa fa-twitter"></i>
													Followers
												</span> <span class="pull-right badge badge-info">+11</span>
											</div>
									</a></li>
								</ul>
							</li>

							<li class="dropdown-footer"><a href="#"> See all
									notifications <i class="ace-icon fa fa-arrow-right"></i>
							</a></li>
						</ul></li>

					<li class="green"><a data-toggle="dropdown"
						class="dropdown-toggle" href="#"> <i
							class="ace-icon fa fa-envelope icon-animated-vertical"></i> <span
							class="badge badge-success">5</span>
					</a>

						<ul
							class="dropdown-menu-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
							<li class="dropdown-header"><i
								class="ace-icon fa fa-envelope-o"></i> 5 Messages</li>

							<li class="dropdown-content">
								<ul class="dropdown-menu dropdown-navbar">
									<li><a href="#" class="clearfix"> <img
											src="assets//avatars/avatar.png" class="msg-photo"
											alt="Alex's Avatar" /> <span class="msg-body"> <span
												class="msg-title"> <span class="blue">Alex:</span>
													Ciao sociis natoque penatibus et auctor ...
											</span> <span class="msg-time"> <i
													class="ace-icon fa fa-clock-o"></i> <span>a moment
														ago</span>
											</span>
										</span>
									</a></li>

									<li><a href="#" class="clearfix"> <img
											src="assets//avatars/avatar3.png" class="msg-photo"
											alt="Susan's Avatar" /> <span class="msg-body"> <span
												class="msg-title"> <span class="blue">Susan:</span>
													Vestibulum id ligula porta felis euismod ...
											</span> <span class="msg-time"> <i
													class="ace-icon fa fa-clock-o"></i> <span>20 minutes
														ago</span>
											</span>
										</span>
									</a></li>

									<li><a href="#" class="clearfix"> <img
											src="assets//avatars/avatar4.png" class="msg-photo"
											alt="Bob's Avatar" /> <span class="msg-body"> <span
												class="msg-title"> <span class="blue">Bob:</span>
													Nullam quis risus eget urna mollis ornare ...
											</span> <span class="msg-time"> <i
													class="ace-icon fa fa-clock-o"></i> <span>3:15 pm</span>
											</span>
										</span>
									</a></li>

									<li><a href="#" class="clearfix"> <img
											src="assets//avatars/avatar2.png" class="msg-photo"
											alt="Kate's Avatar" /> <span class="msg-body"> <span
												class="msg-title"> <span class="blue">Kate:</span>
													Ciao sociis natoque eget urna mollis ornare ...
											</span> <span class="msg-time"> <i
													class="ace-icon fa fa-clock-o"></i> <span>1:33 pm</span>
											</span>
										</span>
									</a></li>

									<li><a href="#" class="clearfix"> <img
											src="assets//avatars/avatar5.png" class="msg-photo"
											alt="Fred's Avatar" /> <span class="msg-body"> <span
												class="msg-title"> <span class="blue">Fred:</span>
													Vestibulum id penatibus et auctor ...
											</span> <span class="msg-time"> <i
													class="ace-icon fa fa-clock-o"></i> <span>10:09 am</span>
											</span>
										</span>
									</a></li>
								</ul>
							</li>

							<li class="dropdown-footer"><a href="inbox.jsp"> See all
									messages <i class="ace-icon fa fa-arrow-right"></i>
							</a></li>
						</ul></li>

					<li class="light-blue"><a href="login.jsp"> <img
							class="nav-user-photo" src="assets//avatars/user.jpg"
							alt="Jason's Photo" />登录<i class="ace-icon fa fa-caret-down"></i>
					</a></li>
				</ul>
			</div>
		</div>
		<!-- /.navbar-container -->
	</div>

	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<div id="sidebar" class="sidebar                  responsive">
			<script type="text/javascript">
				try {
					ace.settings.check('sidebar', 'fixed')
				} catch (e) {
				}
			</script>

			<div class="sidebar-shortcuts" id="sidebar-shortcuts">
				<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
					<button class="btn btn-success">
						<i class="ace-icon fa fa-signal"></i>
					</button>

					<button class="btn btn-info">
						<i class="ace-icon fa fa-pencil"></i>
					</button>

					<button class="btn btn-warning">
						<i class="ace-icon fa fa-users"></i>
					</button>

					<button class="btn btn-danger">
						<i class="ace-icon fa fa-cogs"></i>
					</button>
				</div>

				<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
					<span class="btn btn-success"></span> <span class="btn btn-info"></span>

					<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
				</div>
			</div>
			<!-- /.sidebar-shortcuts -->

			<ul class="nav nav-list">
				<li class="active"><a href="/Library/admin/index.jsp"> <i
						class="menu-icon fa fa-tachometer"></i>主页
				</a> <b class="arrow"></b></li>

				<li class=""><a href="#" class="dropdown-toggle"> <i
						class="menu-icon fa fa-list"></i>借阅管理<b
						class="arrow fa fa-angle-down"></b>
				</a> <b class="arrow"></b>

					<ul class="submenu">
						<li class=""><a href="/Library/admin/borrowrecord.jsp"> <i
								class="menu-icon fa fa-caret-right"></i> 借阅记录
						</a> <b class="arrow"></b></li>

						<li class=""><a href="/Library/admin/brokerules.jsp"> <i
								class="menu-icon fa fa-caret-right"></i> 违章记录
						</a> <b class="arrow"></b></li>
						<li class=""><a href="/Library/admin/borrow.jsp"> <i
								class="menu-icon fa fa-caret-right"></i> 借阅书籍
						</a> <b class="arrow"></b></li>
						<li class=""><a href="/Library/admin/back.jsp"> <i
								class="menu-icon fa fa-caret-right"></i> 归还书籍
						</a> <b class="arrow"></b></li>
					</ul></li>

				<li class=""><a href="/Library/admin/bookmanage.jsp"> <i
						class="menu-icon fa fa-file-o"></i>图书管理<b class="arrow"></b>
				</a> <b class="arrow"></b></li>

				<li class=""><a href="/Library/admin/member.jsp"> <i
						class="menu-icon fa fa-list-alt"></i>会员管理
				</a> <b class="arrow"></b></li>

				<li class=""><a href="/Library/admin/information.jsp"> <i
						class="menu-icon fa fa-calendar"></i> <span class="menu-text">我的信息<span
							class="badge badge-transparent tooltip-error"
							title="2 Important Events"> <i
								class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
						</span>
					</span>
				</a> <b class="arrow"></b></li>
				<li class=""><a href="system.jsp"> <i
						class="menu-icon fa fa-list-alt"></i>系统设置
				</a> <b class="arrow"></b></li>
			</ul>
			</li>
			</ul>
			<!-- /.nav-list -->

			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i class="ace-icon fa fa-angle-double-left"
					data-icon1="ace-icon fa fa-angle-double-left"
					data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>

			<script type="text/javascript">
				try {
					ace.settings.check('sidebar', 'collapsed')
				} catch (e) {
				}
			</script>
		</div>

		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try {
							ace.settings.check('breadcrumbs', 'fixed')
						} catch (e) {
						}
					</script>

					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> <a
							href="index.jsp">图书馆管理系统</a></li>

						<li>归还书籍</li>
					</ul>
					<!-- /.breadcrumb -->

					<div class="nav-search" id="nav-search">
						<form class="form-search">
							<span class="input-icon"> <input type="text"
								placeholder="Search ..." class="nav-search-input"
								id="nav-search-input" autocomplete="off" /> <i
								class="ace-icon fa fa-search nav-search-icon"></i>
							</span>
						</form>
					</div>
					<!-- /.nav-search -->
				</div>

				<div class="page-content">
					<div class="ace-settings-container" id="ace-settings-container">
						<div class="btn btn-app btn-xs btn-warning ace-settings-btn"
							id="ace-settings-btn">
							<i class="ace-icon fa fa-cog bigger-130"></i>
						</div>

						<div class="ace-settings-box clearfix" id="ace-settings-box">
							<div class="pull-left width-50">
								<div class="ace-settings-item">
									<div class="pull-left">
										<select id="skin-colorpicker" class="hide">
											<option data-skin="no-skin" value="#438EB9">#438EB9</option>
											<option data-skin="skin-1" value="#222A2D">#222A2D</option>
											<option data-skin="skin-2" value="#C6487E">#C6487E</option>
											<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
										</select>
									</div>
									<span>&nbsp; Choose Skin</span>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-navbar" /> <label class="lbl"
										for="ace-settings-navbar"> Fixed Navbar</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-sidebar" /> <label class="lbl"
										for="ace-settings-sidebar"> Fixed Sidebar</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-breadcrumbs" /> <label class="lbl"
										for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-rtl" /> <label class="lbl"
										for="ace-settings-rtl"> Right To Left (rtl)</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-add-container" /> <label class="lbl"
										for="ace-settings-add-container"> Inside <b>.container</b>
									</label>
								</div>
							</div>
							<!-- /.pull-left -->

							<div class="pull-left width-50">
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-hover" /> <label class="lbl"
										for="ace-settings-hover"> Submenu on Hover</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-compact" /> <label class="lbl"
										for="ace-settings-compact"> Compact Sidebar</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2"
										id="ace-settings-highlight" /> <label class="lbl"
										for="ace-settings-highlight"> Alt. Active Item</label>
								</div>
							</div>
							<!-- /.pull-left -->
						</div>
						<!-- /.ace-settings-box -->
					</div>
					<!-- /.ace-settings-container -->

					<div class="page-header">
						<h1>归还书籍</h1>
					</div>
					<!-- /.page-header -->

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->

							<div class="search radius6">
	<form name="searchform" method="post" action="SearchBorrow">
		
        <input class="inp_srh" name="bookid"  placeholder="请输入您要搜索的书籍" >

		<input class="btn_srh" type = "submit" name = "借书" value = "搜索" >
		&nbsp;&nbsp;
		
		<input class="btn_srh" type="submit" name="submit1" value="扫描">
	</form>
</div>


							<script type="text/javascript"
								src="assets//js/jquery-1.8.3.min.js"></script>
							<script type="text/javascript"
								src="assets//js/jquery.select.js"></script>
							<hr />
							<%
								//$index=$_Get('keyboard');
								

								while (book.next()) {
							%>


							<div class="profile-info-row">
								<div class="profile-info-name">图书名:</div>

								<div class="profile-info-value">
									<span class="editable" id="username"><%=book.getString("bookname")%></span>
								</div>
							</div>

							<div class="profile-info-row">
								<div class="profile-info-name">索书号 :</div>

								<div class="profile-info-value">
									<i class="fa fa-map-marker light-orange bigger-110"></i> <span
										class="editable" id="country"><%=book.getString("index")%></span><span
										class="editable" id="city"></span>
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">图书编号:</div>
								<% 
								bookid=book.getString("bookID"); 
								%>

								<div class="profile-info-value">
									<span class="editable" id="signup"><%=bookid%></span>
								</div>
							</div>

							<div class="profile-info-row">
								<div class="profile-info-name">借书者 :</div>

								<div class="profile-info-value">
									<span class="editable" id="age"><%=book.getString("name")%></span>
								</div>
							</div>

							<div class="profile-info-row">
								<div class="profile-info-name">借阅证号:</div>
								<% 
								cardid=book.getString("cardID"); 
								%>

								<div class="profile-info-value">
									<span class="editable" id="signup"><%=cardid%></span>
								</div>
							</div>

							<div class="profile-info-row">
								<div class="profile-info-name">借书日期:</div>

								<div class="profile-info-value">
									<span class="editable" id="about"><%=book.getString("bo_date")%></span>
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">截止日期:</div>

								<div class="profile-info-value">
									<span class="editable" id="about"><%=book.getString("deadline")%></span>
								</div>
							</div>
							<hr />
							<%
								}
							%>


						</div>
						<hr />
						<button class="btn btn-info" type="button" value="还书"
							onclick="BookBack()">
							还书<i class="ace-icon fa fa-check bigger-110"></i>
						</button>
						
						<script>
							function BookBack() {
								$.ajax({
									type:'post',
									data:{bookid:<%= bookid %>,cardid:<%= cardid%>},
									url:'./return_book',
									success:function(data){
										alert("还书成功")
									}
								});
	
							};
						</script>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


						<script type="text/javascript">
							var $path_base = ".";//in Ace demo this will be used for editurl parameter
						</script>

						<!-- PAGE CONTENT ENDS -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->


	<a href="#" id="btn-scroll-up"
		class="btn-scroll-up btn btn-sm btn-inverse"> <i
		class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
	</a>
	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->

	<!--[if !IE]> -->
	<script src="assets//js/jquery.2.1.1.min.js"></script>

	<!-- <![endif]-->

	<!--[if IE]>
<script src="assets//js/jquery.1.11.1.min.js"></script>
<![endif]-->

	<!--[if !IE]> -->
	<script type="text/javascript">
		window.jQuery
				|| document.write("<script src='assets//js/jquery.min.js'>"
						+ "<"+"/script>");
	</script>

	<!-- <![endif]-->

	<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets//js/jquery1x.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document
					.write("<script src='assets//js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="assets//js/bootstrap.min.js"></script>

	<!-- page specific plugin scripts -->
	<script src="assets//js/bootstrap-datepicker.min.js"></script>
	<script src="assets//js/jquery.jqGrid.min.js"></script>
	<script src="assets//js/grid.locale-en.js"></script>

	<!-- ace scripts -->
	<script src="assets//js/ace-elements.min.js"></script>
	<script src="assets//js/ace.min.js"></script>

	<!-- inline scripts related to this page -->
	
</body>
</html>
