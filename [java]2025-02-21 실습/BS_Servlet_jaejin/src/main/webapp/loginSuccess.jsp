<%@ page import="org.example.bs_servlet_jaejin.vo.MemberVO" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>로그인 성공</title>
</head>
<body>
<h1>로그인 성공</h1>

<%
    MemberVO currentUser = (MemberVO) session.getAttribute("member");
    String username = currentUser.getMember_name();

    System.out.println("username: " + username);
    if (username != null) {
%>
<p>안녕하세요, <strong><%= username %></strong>님!</p>
<form action="welcome">
    <button type="submit">게시판 보기</button>
</form> <br>

<!-- 로그아웃 버튼 -->
<form action="login.html" method="post">
    <button type="submit">로그아웃</button>
</form>
<%
    } else {
        session.invalidate();
        response.sendRedirect("login.html");
    }
%>
</body>
</html>