<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="gq.bookfarm.vo.CategoryVO"%>
<%@page import="org.apache.log4j.Logger"%>

<%! private final Logger log = Logger.getLogger(this.getClass()); %>

<%
	String				currentPage	= request.getParameter("page");
	Vector<CategoryVO>	categories	= (Vector<CategoryVO>) request.getAttribute("categories");
	CategoryVO			category	= (CategoryVO) request.getAttribute("category");
%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>카테고리 추가</title>
<script>
	function register_check(form){
		if (form.category_name.value.length==0) {
			alert('카테고리명을 입력해주세요.');
			form.id.focus();
			return;
		} else {
			form.submit();
		}
	}
	
	function adminCategoryList() {
		location.href="./adminCategoryList.do?page=<%=currentPage%>";
	}
</script>
</head>
<body>
	<form action="./adminCategoryModify.do?page=<%=currentPage%>&idx=<%=category.getIdx()%>" method="post">
		<table>
			<caption>카테고리 수정</caption>	
			<tr>
				<td><label>상위 카테고리</label>
				<td>
					<select name='partent_idx'>
<%					for(CategoryVO cat: categories) {	%>
						<option value="<%=cat.getParent_idx()%>" <%if(cat.getParent_idx() == category.getParent_idx())	out.print("selected");%>><%=cat.getCategory_name()%></option>
						
<%					} %>
					</select>
				</td>
			</tr>
			<tr>
				<td><label>카테고리 이름</label>
				<td><input type="text" name="category_name" value="<%=category.getCategory_name()%>"></td>
			</tr>
			<tr>
				<td colspan="2" class="btn_align">
					<input type="button" name="add"		value="수정"			onclick="register_check(this.form)">
					<input type="button" name="cancel"	value="취소"			onclick="javascript:history.back()">
					<input type="button" name="list"	value="카테고리 목록"	onclick="adminCategoryList()">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>