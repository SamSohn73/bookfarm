<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Vector"%>
<%@ page import="gq.bookfarm.vo.AdminVO"%>
<%@ page import="gq.bookfarm.vo.PageVO"%>
<%@ page import="gq.bookfarm.vo.OrdersVO"%>

<%! private final Logger log = Logger.getLogger(this.getClass()); %>

<%
	AdminVO				adminVO		= (AdminVO)session.getAttribute("adminVO");
	PageVO				pageInfo	= (PageVO) request.getAttribute("pageInfo");
	Vector<OrdersVO>	orders		= (Vector<OrdersVO>) request.getAttribute("orders");
	
	String criteria			= request.getParameter("criteria");
	String searchWord		= request.getParameter("searchWord");
	
	if (criteria == null)	criteria	= "";
	if (searchWord == null)	searchWord	= "";
	
	int currentPage	= pageInfo.getPage();
	int startPage	= pageInfo.getStartPage();
	int endPage		= pageInfo.getEndPage();
	int totalRows	= pageInfo.getTotalRows();
	int totalPages	= pageInfo.getTotalPages();
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="Online Bookstore Bookfarm">
		<meta name="author" content="BookFarmer">
		<link rel="shortcut icon" href="../favicon.ico">
		
		<title>BOOKFARM Order Management</title>
	
		<!-- Bootstrap core CSS -->
		<!--<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">-->
		<link href="vendor/bootstrap/css/bootstrap.css" rel="stylesheet">
	
		<!-- Custom styles for this template -->
		<link href="css/shop-homepage.css" rel="stylesheet">

		<script type="text/javascript">
			function search(){
				if(searchform.searchWord.value==""){
					alert('검색어를 넣으세요');
					searchform.searchWord.focus();
					return;
				}
				searchform.submit();
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
		
		
				<div class="col-lg-9">
					<h2 class="my-4">주문 목록</h2>
					<table class="table">
<%
	int idNum = totalRows - (currentPage-1)*10;
	for(OrdersVO order: orders) {%>
						<tr align="left">
							<td class="table-secondary">No.</td>
							<td class="bg-light" colspan="5"><%=idNum%></td>
						</tr>
						<tr align="left">
						<tr>
							<td class="table-secondary">주문자 idx</td>
							<td class="bg-light" colspan="2"><%=order.getCustomers_idx()%></a>
							</td>
							<td class="table-secondary">수신인</td>
							<td class="bg-light" colspan="2"><%=order.getDelivery_name()%></td>
						</tr>
						<tr>
							<td class="table-secondary">우편번호</td>
							<td class="bg-light" colspan="5"><%=order.getDelivery_postcode()%></td>
						</tr>
						<tr>
							<td class="table-secondary">주소1</td>
							<td class="bg-light" colspan="5"><%=order.getDelivery_address1()%></td>
						</tr>
						<tr>
							<td class="table-secondary">주소2</td>
							<td class="bg-light" colspan="5"><%=order.getDelivery_address2()%></td>
						</tr>
						<tr>
							<td class="table-secondary">전화번호 1</td>
							<td class="bg-light" colspan="2"><%=order.getDelivery_phone1()%></td>
							<td class="table-secondary">이메일 1</td>
							<td class="bg-light" colspan="2"><%=order.getDelivery_email1()%></td>
						</tr>
						<tr>
							<td class="table-secondary">결재자</td>
							<td class="bg-light" colspan="5"><%=order.getBilling_name()%></td>
						</tr>
						<tr>							
							<td class="table-secondary">우편번호</td>
							<td class="bg-light" colspan="5"><%=order.getBilling_postcode()%></td>
						</tr>
						<tr>	
							<td class="table-secondary">주소1</td>							
							<td class="bg-light" colspan="5"><%=order.getBilling_address1()%></td>
						</tr>
						<tr>
							<td class="table-secondary">주소2</td>							
							<td class="bg-light" colspan="5"><%=order.getBilling_address2()%></td>
						</tr>
						<tr>
							<td class="table-secondary">전화번호 1</td>
							<td class="bg-light" colspan="2"><%=order.getBilling_phone1()%></td>							
							<td class="table-secondary">이메일 1</td>
							<td class="bg-light" colspan="2"><%=order.getBilling_email1()%></td>
						</tr>
						<tr>
							<td class="table-secondary">결재방법</td>
							<td class="bg-light"><%=order.getPayment_method()%></td>							
							<td class="table-secondary">결재가격</td>
							<td class="bg-light"><%=order.getFinal_price()%></td>
							<th class="table-secondary">주문상태</th>
							<td class="bg-light"><%=order.getOrders_status()%></td>
						</tr>
						<tr>
							<td class="table-secondary">구매일</td>
							<td class="bg-light"><%=order.getDate_purchased()%></td>
							<td class="table-secondary">최종수정일</td>
							<td class="bg-light"><%=order.getLast_modified()%></td>							
							<td class="table-secondary">주문완료일</d>
							<td class="bg-light"><%=order.getOrders_date_finished()%></td>							
						</tr>
						<tr>
						<td colspan="6"></td>
						</tr>
<%		idNum--;
	} %>
						<tr>
						<td colspan = "17">
							<%//[prev] display
								if (currentPage > 1) {
									out.print("<a href=adminOrdersList.do?page=" + (currentPage-1) + ">");
									out.print("[prev] </a>");
								}
							%>
							<%//page numbers display
								for (int i = startPage; i <= endPage; i++) {
									if (i == currentPage) {
										out.print("[" +  i + "] ");
									} else {
										out.print("<a href=adminOrdersList.do?page=" + i +">");
										out.print(i + " </a>");
									}
								}
							%>
							<%//[next] display
								if (currentPage <= endPage && currentPage < totalPages) {
									out.print("<a href=adminOrdersList.do?page=" + (currentPage + 1) + ">");
									out.print(" [next]</a>");
								}
							%>
						</td>
					</tr>
					</table>
					
					<table>
						<tr>
							<td class="td_align">
								<form action='AdminOrdersearch.do' method='post' name='searchform'>
									<select name='criteria'>
										<option value='orders_name'				<%if(criteria.equals("orders_name"))			out.print("selected");%>>이름</option>
										<option value='orders_address'			<%if(criteria.equals("orders_address"))			out.print("selected");%>>주소</option>
										<option value='orders_phone'			<%if(criteria.equals("orders_phone"))			out.print("selected");%>>전화번호</option>
										<option value='orders_email'			<%if(criteria.equals("orders_email"))			out.print("selected");%>>이메일</option>
										<option value='orders_payment_method'	<%if(criteria.equals("orders_payment_method"))	out.print("selected");%>>결제방법</option>
									</select>
									<input type='text' name='searchWord' value="<%=searchWord%>">
									<input type='button' value='검색' onclick="search()">						
								</form>
							</td>
							<!--  <td align='right'><a href="admin/admin_add.jsp?page=<%=currentPage%>">[사용자 추가]</a></td>  -->
						</tr>
					</table>
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
