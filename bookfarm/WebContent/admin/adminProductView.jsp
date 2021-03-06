<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@page import="java.util.Vector"%>
<%@ page import="gq.bookfarm.vo.AdminVO"%>
<%@ page import="gq.bookfarm.vo.PageVO"%>
<%@ page import="gq.bookfarm.vo.ProductVO"%>
<%@ page import="gq.bookfarm.vo.CategoryVO"%>

<%! private final Logger log = Logger.getLogger(this.getClass()); %>

<%
	AdminVO				adminVO			= (AdminVO)session.getAttribute("adminVO");
	Vector<CategoryVO>	categories		= (Vector<CategoryVO>) session.getAttribute("categories");
	ProductVO			productVO		= (ProductVO)request.getAttribute("productVO");
	String				current_page	= request.getParameter("page");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="Online Bookstore Bookfarm">
		<meta name="author" content="BookFarmer">
		<link rel="shortcut icon" href="../favicon.ico">
		
		<title>BOOKFARM Product Detail</title>
	
		<!-- Bootstrap core CSS -->
		<!--<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->
		<link href="vendor/bootstrap/css/bootstrap.css" rel="stylesheet">
	
		<!-- Custom styles for this template -->
		<link href="css/shop-homepage.css" rel="stylesheet">

		<script>
			function delete_list(){
				location.href="adminProductDelete.do?idx=<%=productVO.getIdx()%>&page=<%=current_page%>";
			}
		</script>
	</head>
	
	<body>
		<!-- Navigation -->
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
			<div class="container">
				<a class="navbar-brand" href="index.do">Bookfarm - Admin Page</a>
				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarResponsive">
					<ul class="navbar-nav ml-auto">
						<li class="nav-item active">
							<a class="nav-link" href="index.do">Home
								<span class="sr-only">(current)</span>
							</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="adminCustomerList.do">고객관리</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="adminProductList.do">상품관리</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="adminOrdersList.do">주문관리</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="adminCategoryList.do">카테고리관리</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="adminReviewsList.do">리뷰관리</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="adminLogout.do">로그아웃</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>

		<!-- Page Content -->
		<div class="container">
	
			<div class="row">
	
				<div class="col-lg-3">
		
					<h1 class="my-4">책팜</h1>

				</div>
				<!-- /.col-lg-3 -->
		
		
				<div class="col-lg-9 my-5">
				<form action="./adminProductModify.do" method="post" enctype="multipart/form-data">
					<table>
						<caption>상품 보기</caption>
						<tr>
							<td class="td_left">번 호</td>
							<td class="td_right">
							<input type='text' name='idx' size="64" readonly value="<%=productVO.getIdx() %>">
							</td>
						</tr>
						<tr>
							<td class="td_left">카테고리</td>
							<td class="td_right">
								<select name='category_idx'>
			<%	for(CategoryVO category: categories) {%>
									<option value="<%=category.getIdx()%>" <%if(productVO.getCategory_idx() == category.getIdx()) out.print("selected");%>><%=category.getCategory_name()%></option>
			<%	} %>
								</select>
							</td>
						</tr>
						<tr>
							<td class="td_left">상품명</td>
							<td class="td_right">
							<input type='text' name='product_name' size="64" value="<%=productVO.getProduct_name() %>">
							</td>
						</tr>
						<tr>
							<td class="td_left">상품 이미지</td>
							<td class="file_td">
							<%
							String fileName = productVO.getProduct_image();
							if(fileName!=null){
								fileName=new String(fileName.getBytes(),"UTF-8");
							%>
								<img src="<%=fileName%>">
								
							<%} else {%><!-- 처음 입력시 파일을 안 넣었다면 수정하는 부분에서만 넣는 것 허용 -->
								<input type="file" name="product_image" id="product_image">
							<%} %>
							</td>
						</tr>
						<tr>
							<td class="td_left">재고 수량</td>
							<td class="td_right">
								<input type='text' name='product_quantity' size="64" value="<%=productVO.getProduct_quantity() %>">
							</td>
						</tr>
						<tr>
							<td class="td_left">가격</td>
							<td class="td_right">
								<input type='text' name='product_price' size="64" value="<%=productVO.getProduct_price() %>">
							</td>
						</tr>
						<tr>
							<td class="td_left">제품 설명</td>
							<td class="td_right">
								<textarea name='product_desc' cols='65' rows='15' ><%=productVO.getProduct_desc() %></textarea>
							</td>
						</tr>
						<tr class="button_cell">
							<td colspan='2'>
							<input type='submit' value="수정"> 
							<input type="button" value="삭제" onClick="delete_list()">
							<input type="button" value="목록" onClick="javascript:history.go(-1);">	
							<input type="hidden" name="idx" value=<%=productVO.getIdx() %>>
							<input type="hidden" name="page" value=<%=current_page %>>
							</td>
						</tr>
					</table>
				</form>
				</div>
				<!-- /.col-lg-9 -->
		
			</div>
			<!-- /.row -->
	
		</div>
		<!-- /.container -->
	
		<!-- Footer -->
		<footer class="py-5 bg-dark">
			<div class="container">
				<p class="m-0 text-center text-white">Copyleft &copy; Team Bookfarmer 2018</p>
				<p class="m-0 text-center text-white">DWIT Class3 - Team 책농부</p>
			</div>
			<!-- /.container -->
		</footer>
	
		<!-- Bootstrap core JavaScript -->
		<!--<script src="vendor/jquery/jquery.min.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>-->
		<script src="vendor/jquery/jquery.js"></script>
		<script src="vendor/bootstrap/js/bootstrap.bundle.js"></script>
	</body>
</html>
