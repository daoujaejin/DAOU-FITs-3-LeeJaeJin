<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.bs_servlet_jaejin.vo.BookVO" %>
<%
    BookVO book = (BookVO) request.getAttribute("book");
%>
<html>
<head>
    <title>책 상세 정보</title>
</head>
<body>
<h1>책 상세 정보</h1>
<%
    if (book != null) {
%>
<p><strong>제목:</strong> <%= book.getBtitle() %></p>
<p><strong>저자:</strong> <%= book.getBauthor() %></p>
<p><strong>코드:</strong> <%= book.getBisbn() %></p>
<p><strong>가격:</strong> <%= book.getBprice() %></p>
<%
} else {
%>
<p>책 정보를 찾을 수 없습니다.</p>
<%
    }
%>
<a href="bookList.jsp">목록으로 돌아가기</a>
</body>
</html>
