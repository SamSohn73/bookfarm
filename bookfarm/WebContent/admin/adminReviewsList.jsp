<%@ page import="java.util.Vector"%>
<%@ page import="java.sql.*"%>
<%@ page import="gq.bookfarm.vo.ReviewVO" %>
<%@ page import="gq.bookfarm.vo.CustomerVO" %>
<%@ page import="gq.bookfarm.dao.ReviewDAO" %>
<%@ page import="gq.bookfarm.dao.CustomerDAO" %>
<%@ page import="gq.bookfarm.vo.PageVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
		//입력 받는 변수들
		//Servlet에서 담아놓은 정보 가져오기
		
		
		//VO새로 만들고 거기에 옮기기
		Vector<ReviewVO> list	=	(Vector<ReviewVO>)request.getAttribute("list");
		
		//Informations for Paging		
		int		totalPages		= 1;
		int		currentPage		= 1;
		int		startPage		= 1;
		int		endPage			= 1;
		int		totalRows		= 1;		
		
		if(request.getAttribute("info")!=null)
		{
		PageVO	info			=	(PageVO)request.getAttribute("info");
				totalPages		=	info.getTotalPages();
				currentPage		=	info.getPage();
				startPage		=	info.getStartPage();
				endPage			=	info.getEndPage();
				totalRows		=	info.getTotalRows();
		}		
		//다른 추가정보도 받음
		//DAO declaration for customer username pick up...
		CustomerDAO cDao		=	new CustomerDAO();
									
		//If connection comes through search....
		String	searchCondition	=	(String)request.getAttribute("searchCondition");
		String	searchWord		=	(String)request.getAttribute("searchWord");
		
		//If connection comes from myPage mode....
		//If connection comes from myPage or myList, this category must be needed...
		int		customers_idx	=	0;
		CustomerVO	userVO		=	null;
		if(session.getAttribute("LoginedUserVO")!=null)
		{	
				userVO			=	(CustomerVO)session.getAttribute("LoginedUserVO");
				customers_idx	=	userVO.getIdx();
		}
		
		String	type			=	(String)request.getParameter("type");
		
		//If connection comes from indivisual item, products_idx must be needed...		
		int		products_idx	=	0;
		if(request.getParameter("products_idx")!=null)
				products_idx	=	Integer.parseInt((String)request.getParameter("products_idx"));
		
		
		//불러올 CSS
		/*
		클래스_테이블1
		클래스_tr타이틀1
		클래스_tr_top1
		클래스_td_align1
		클래스_bottom_table1
		클래스_td_align1
		클래스_btn_align1
		클래스_btn1
		*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰창</title>
	<script>
		function search()
		{
			if(searchform.searchWord.value=="")
			{
				alert('검색어를 넣으세요');
				searchform.searchWord.focus();
				return;
			}
				searchform.submit();
		}
	</script>
</head>
<body>
		<table class="클래스_테이블1">
			<caption>
			리뷰게시판
			</caption>
			<tr class="클래스_tr타이틀1">
				<th >날짜</th>
				<th >제목</th>
				<th >작성자</th>
				<th >조회수</th>
			</tr>
			<%for(ReviewVO vo:list)
				{%>
				<tr class="클래스_tr_top1">
					<td><%=vo.getDate_added()%></td>
					<td align="left"><a href="qAdminReviewsView.do?idx=<%=vo.getIdx()%>
					&page=<%=currentPage%>&type=<%=type%>">
					<%=vo.getReview_title()%></a></td>
					<td><%=cDao.getName(vo.getCustomers_idx())%></td>
					<td><%=vo.getReviews_read()%></td>
				</tr>
				<%}%>
<tr>
	<td colspan="5">
		<%	if(searchWord == null)
			{
				if(currentPage<=1)
				{
						out.print("");
				}
				else
				{
						out.print("<a href=./qAdminReviewsLists.do?page="+(currentPage-1)+
								"&products_idx="+products_idx+
								"&type="+type+
								">");
						out.print("[이전]</a>");
				}
				for(int i=startPage;i<=endPage;i++)
				{
					if(i==currentPage)
					{
						out.print("["+i+"]");
					}
					else
					{					
						out.print("<a href=./qAdminReviewsLists.do?page="+i+
								"&products_idx="+products_idx+
								"&type="+type+
								">");
						out.print(i+"</a>");
					}
				}
				if(currentPage>=totalPages)
				{
						out.print("");
				}
				else
				{
						out.print("<a href=./qAdminReviewsLists.do?page="+(currentPage+1)+
								"&products_idx="+products_idx+
								"&type="+type+
								">");
						out.print("[다음]</a>");
				}
			}
			else
			{
				if(currentPage<=1)
				{
					out.print("");
				}
				else
				{
					out.print("<a href=./qAdminReviewsSearch.do?page="+(currentPage-1)+
							"&searchCondition="+searchCondition+
							"&searchWord="+searchWord+
							"&products_idx="+products_idx+
							"&type="+type+
							">");
					out.print("[이전]</a>");
				}
				for(int i=startPage;i<=endPage;i++)
				{
					if(i==currentPage)
					{
						out.print("["+i+"]");
					}
					else
					{					
						out.print("<a href=./qAdminReviewsSearch.do?page="+i+
								"&searchCondition="+searchCondition+
								"&searchWord="+searchWord+
								"&products_idx="+products_idx+
								"&type="+type+
								">");
						out.print(i+"</a>");
					}
				}
				if(currentPage>=totalPages)
				{
						out.print("");
				}
				else
				{
						out.print("<a href=./qAdminReviewsSearch.do?page="+(currentPage+1)+
								"&searchCondition="+searchCondition+
								"&searchWord="+searchWord+
								"&products_idx="+products_idx+
								"&type="+type+
								">");
						out.print("[다음]</a>");
				}
			}%>
	</td>
</tr>
</table>
<table	class="클래스_bottom_table1">
			<tr>
				<td colspan="5" class="클래스_td_align1">
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td colspan="5" class="클래스_btn_align1">
					<form action="qAdminReviewsSearch.do?products_idx=<%=products_idx %>&type=<%=type%>"
					method="post" name="searchform">
						<select class="btn" name="searchCondition">
							<option value="customers_idx">작성자</option>
							<option value="review_title">제목</option>
							<option value="review_text">글내용</option>
						</select>
						<input type="text" class="클래스_btn1" size="10" name="searchWord" required="required"/>
						<input type="button" class="클래스_btn1" value="검색" onclick="search()">
					</form>
				</td>
				<td align="right">
				<a href="./admin/adminReviewsWrite.jsp?page=<%=currentPage%>
				&products_idx=<%=products_idx %>
				&type=<%=type%>">[글쓰기]</a>
				</td>
				<td align="right">
				<a href="./member/hansol_main_example.jsp">[메인으로]</a>
				</td>
			</tr>
</table>
</body>
</html>