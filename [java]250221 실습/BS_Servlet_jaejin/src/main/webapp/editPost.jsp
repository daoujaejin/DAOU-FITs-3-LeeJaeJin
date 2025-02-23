<%@ page import="org.example.bs_servlet_jaejin.vo.BoardVO" %>
<%@page contentType="text/html; charset=UTF-8" %>

<%
    // 요청 속성에서 게시글 정보 가져오기
    BoardVO post = (BoardVO) request.getAttribute("post");
    if (post == null) {
        response.sendRedirect("welcome");
        return;
    }
%>
<html>
<head>
    <title>게시글 수정</title>
</head>
<body>
<h1>게시글 수정</h1>

<form action="editPost" method="post">
    <!-- 게시글 ID를 숨겨진 필드로 전달 -->
    <input type="hidden" name="id" value="<%= post.getBoard_id() %>" />

    <!-- 제목 입력 -->
    <label for="title">제목:</label><br />
    <input type="text" id="title" name="title" value="<%= post.getTitle() %>" required /><br /><br />

    <!-- 내용 입력 -->
    <label for="content">내용:</label><br />
    <textarea id="content" name="content" rows="10" cols="50" required><%= post.getContent() %></textarea><br /><br />

    <!-- 제출 버튼 -->
    <button type="submit">수정 완료</button>
</form>

<a href="welcome">목록으로 돌아가기</a>
</body>
</html>
