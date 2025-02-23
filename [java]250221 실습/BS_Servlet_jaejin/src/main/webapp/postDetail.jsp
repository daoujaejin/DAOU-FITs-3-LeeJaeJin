
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.bs_servlet_jaejin.vo.BoardVO" %>
<%@ page import="org.example.bs_servlet_jaejin.vo.MemberVO" %>
<%@ page import="org.example.bs_servlet_jaejin.vo.CommentVO" %>
<%@ page import="java.util.List" %>
<%
    // 세션에서 현재 로그인한 사용자 정보 가져오기
    MemberVO currentUser = (MemberVO) session.getAttribute("member");

    // 요청 속성에서 게시글 정보 가져오기
    BoardVO post = (BoardVO) request.getAttribute("post");
    if (post == null) {
        // 게시글 정보가 없으면 목록 페이지로 리다이렉트
        response.sendRedirect("welcome");
        return;
    }
    boolean isLikedByCurrentUser = (boolean) request.getAttribute("isLikedByCurrentUser"); // 서버에서 전달된 값
%>
<html>
<head>
    <title>게시글 상세 정보</title>
    <!-- jQuery CHN -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>
<h1><%= post.getTitle() %></h1>

<p><strong>작성자:</strong> <%= post.getWriter_id() %></p>
<p><strong>작성일:</strong> <%= post.getCreated_at() %></p>
<p><strong>내용:</strong></p>
<p><%= post.getContent() %></p>

<%
    // 현재 로그인한 사용자와 작성자가 동일한 경우 삭제 버튼 표시
    if (currentUser != null && currentUser.getMember_id().equals(post.getWriter_id())) {
%>
<form action="deletePost" method="post">
    <input type="hidden" name="id" value="<%= post.getBoard_id() %>" />
    <button type="submit">삭제하기</button>
</form>
<form action="editPost" method="get">
    <input type="hidden" name="id" value="<%= post.getBoard_id() %>" />
    <button type="submit">수정하기</button>
</form>
<%
    }
%>

<%
    if (currentUser != null && !currentUser.getMember_id().equals(post.getWriter_id())) {
%>
<button id="likeButton" data-liked="<%= isLikedByCurrentUser %>">
    <%= isLikedByCurrentUser ? "좋아요 취소" : "좋아요" %>
</button>
<%
        System.out.println("좋아요: " + isLikedByCurrentUser);
    }
%>

<a href="welcome">목록으로 돌아가기</a>

<div id="commentsSection">
    <!-- 기존 댓글 출력 -->
    <ul id="commentsList">
        <%
            List<CommentVO> comments = (List<CommentVO>) request.getAttribute("comments");
            for (CommentVO comment : comments) {
        %>
        <li>
            <strong><%= comment.getUser_id() %></strong>: <%= comment.getContent() %>
            <% if (currentUser != null && currentUser.getMember_id().equals(comment.getUser_id())) { %>
            <button class="deleteCommentButton" data-id="<%= comment.getId() %>">삭제</button>
            <% } %>
        </li>
        <% } %>
    </ul>

    <!-- 댓글 입력 -->
    <textarea id="commentContent"></textarea>
    <button id="addCommentButton">댓글 작성</button>
</div>

<script>
    // 댓글 추가 처리
    $('#addCommentButton').on('click', function () {
        const content = $('#commentContent').val();
        console.log("컨텐츠: " + content);

        $.ajax({
            url: 'addComment',
            method: 'POST',
            data: {
                boardId: '<%= post.getBoard_id() %>',
                content: content
            },
            success: function () {
                location.reload(); // 새로고침하여 댓글 갱신
            },
            error: function () {
                alert('댓글 작성 중 오류가 발생했습니다.');
            }
        });
    });

    // 댓글 삭제 처리
    $('.deleteCommentButton').on('click', function () {
        const commentId = $(this).data('id');

        $.ajax({
            url: 'deleteComment',
            method: 'POST',
            data: {
                boardId: '<%= post.getBoard_id() %>',
                commentId: commentId
            },
            success: function () {
                location.reload(); // 새로고침하여 댓글 갱신
            },
            error: function () {
                alert('댓글 삭제 중 오류가 발생했습니다.');
            }
        });
    });
</script>

<script>
    // 좋아요 버튼 클릭 이벤트 처리
    $('#likeButton').on('click', function () {
        const isLiked = $(this).data('liked');
        const action = isLiked ? 'unlike' : 'like';
        console.log(action);

        $.ajax({
            url: 'likePost',
            method: 'POST',
            data: { boardId: '<%= post.getBoard_id() %>', action: action },
            success: function () {
                if (isLiked) {
                    $('#likeButton').text('좋아요').data('liked', false);
                } else {
                    $('#likeButton').text('좋아요 취소').data('liked', true);
                }
            },
            error: function () {
                alert('좋아요 작업 중 오류가 발생했습니다.');
            }
        });
    });
</script>

</body>
</html>
