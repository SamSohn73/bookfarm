<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="gq.bookfarm.vo.PageVO"%>
<%@page import="gq.bookfarm.vo.ProductVO"%>
<%@page import="gq.bookfarm.vo.CategoryVO"%>
<%@page import="org.apache.log4j.Logger"%>

<%! private final Logger log = Logger.getLogger(this.getClass()); %>
<%
	Vector<CategoryVO>	categories		= (Vector<CategoryVO>) session.getAttribute("categories");
	ProductVO			productVO		= (ProductVO)request.getAttribute("productVO");
	String				current_page	= request.getParameter("page");
%>
<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<title>상품 보기</title>
<script>
	function delete_list(){
		location.href="adminProductDelete.do?idx=<%=productVO.getIdx()%>&page=<%=current_page%>";
	}
</script>
</head>
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
</html>