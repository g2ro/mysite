<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list}" var = "board" varStatus="status">
						<tr>
							<td>${listSize - (status.index + (pageSize * (currentPage - 1))) }</td>
								<td style="text-align:left; padding-left:${board.depth * 20}px">
									<c:if test="${board.depth > 0}">
										<img src="${pageContext.request.contextPath }/assets/images/reply.png">			
									</c:if>
										<a href="board?a=view&id=${board.id }&kwd=${kwd}">${board.title }</a>
								</td>
								<td>${board.userName }</td>
								<td>${board.hit }</td>
								<td>${board.reg_date }</td>
								<c:if test="${not empty sessionScope.authUser && sessionScope.authUser.id == board.userId }">
									<td><a href="board?a=delete&id=${board.id }" class="del">삭제</a></td>
								</c:if>		
						</tr>
					</c:forEach>
					
					<c:if test="${empty list }">
						<tr>
                            <td colspan="5">게시글이 없습니다.</td>
                        </tr>
					</c:if>		
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">
					<ul>
						<c:if test="${currentPage > 1 }">
							<li><a href="board?p=${currentPage - 1 }&kwd=${kwd}">◀</a></li>
						</c:if>
								<c:forEach var= "page" begin="${startPage }" end="${endPage }">
									<c:choose>
										<c:when test="${currentPage == page }">
											<li class="selected"><a href="board?p=${page }&kwd=${kwd}">${page }</a></li>
										</c:when>
										<c:otherwise>
											<li ><a href="board?p=${page }&kwd=${kwd}">${page }</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						<c:if test="${currentPage < totalPage }">
							<li><a href="board?p=${currentPage + 1 }&kwd=${kwd}">▶</a></li>
						</c:if>
					</ul>
				</div>
				<div class="bottom">
					<c:if test="${not empty sessionScope.authUser }">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>