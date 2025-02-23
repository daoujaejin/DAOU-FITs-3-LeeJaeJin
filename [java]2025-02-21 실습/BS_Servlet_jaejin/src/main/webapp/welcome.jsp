<%@ page import="org.example.bs_servlet_jaejin.vo.MemberVO" %>
<%@ page import="org.example.bs_servlet_jaejin.vo.BoardVO" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html; charset=UTF-8" %>

<html>
<head>
    <title>게시판</title>
</head>
<body>
<%
    // 세션에서 member 객체 가져오기
    MemberVO member = (MemberVO) session.getAttribute("member");

    if (member == null) {
%>
<h1>로그인이 필요합니다.</h1>
<a href="login.html">로그인 페이지로 이동</a>
<%
        return; // 더 이상 실행하지 않음
    }

    // 게시글 목록 가져오기
     List<BoardVO> posts = (List<BoardVO>) request.getAttribute("posts");
    System.out.println("post size: " + posts.size());
%>

<h1><%= member.getMember_name() %>님 환영합니다!</h1>

<!-- 게시글 작성 버튼 -->
<a href="writePost.jsp">
    <button>게시글 작성</button>
</a>

<h2>게시판 글 목록</h2>
<form action="welcome" method="get">
    검색: <input type="text" name="keyword" />
    <button type="submit">검색</button>
</form>

<table border="1">
    <tr>
        <th>번호</th>
        <th>제목</th>
        <th>내용</th>
        <th>작성자</th>
        <th>작성일</th>
        <th>댓글 수</th>
        <th>좋아요 수</th>
        <th>글 조회수</th>
    </tr>
    <%
        if (posts != null) {
            for (BoardVO post : posts) {
    %>
    <tr>
        <td><%= post.getBoard_id() %></td>
        <td><%= post.getTitle() %></td>
        <td><%= post.getContent() %></td>
        <td><%= post.getWriter_id() %></td>
        <td><%= post.getCreated_at() %></td>
        <td><%= post.getComment_count() %></td>
        <td><%= post.getLike_count() %></td>
        <td><%= post.getView_count() %></td>
        <!-- 상세보기 버튼 -->
        <td><a href="postDetail?id=<%=post.getBoard_id()%>">상세보기</a></td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="5">게시글이 없습니다.</td>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>
