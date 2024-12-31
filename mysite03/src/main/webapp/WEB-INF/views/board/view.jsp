<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% pageContext.setAttribute("newLine", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${Bvo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(Bvo.contents, newLine, "<br>") }
							</div>
						</td>
					</tr>
				</table>
				<div class="bottom">
					<form class="board-form" method="post"
						action="${pageContext.request.contextPath }/board">
						<input type="submit" value="글목록">
					</form>
					<c:if test="${not empty sessionScope.authUser && sessionScope.authUser.id == Bvo.userId }">
						<form class="board-form" method="post" action="${pageContext.request.contextPath }/board/modify">
							 <input type="hidden" name="id" value="${Bvo.id}"> 
							 <input type="hidden" name="userId" value="${Bvo.userId}">
							 <input type="submit" value="글수정">
						</form>
					</c:if>
					<c:if test="${not empty sessionScope.authUser}">
						<%-- <form class="board-form" method="get" action="${pageContext.request.contextPath }/board/write/${Bvo.id}"> --%>
							<a href="${pageContext.request.contextPath }/board/write/${Bvo.id}"><input type="button" value="답글"></a>
						<!-- </form> -->
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
        <c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>