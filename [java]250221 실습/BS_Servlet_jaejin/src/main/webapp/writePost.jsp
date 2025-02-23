<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>게시글 작성</title>
</head>
<body>

<h1>게시글 작성</h1>

<form action="writePost" method="post">
    <label for="title">제목:</label><br/>
    <input type="text" id="title" name="title" required/><br/><br/>

    <label for="content">내용:</label><br/>
    <textarea id="content" name="content" rows="10" cols="50" required></textarea><br/><br/>

    <button type="submit">작성 완료</button>
</form>

<a href="welcome">취소</a>

</body>
</html>
