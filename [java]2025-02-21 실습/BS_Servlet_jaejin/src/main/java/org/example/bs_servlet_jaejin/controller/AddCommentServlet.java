package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.dao.BoardDAO;
import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
import org.example.bs_servlet_jaejin.vo.CommentVO;
import org.example.bs_servlet_jaejin.vo.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/addComment")
public class AddCommentServlet extends HttpServlet {

    private final SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final BoardDAO boardDAO = new BoardDAO(factory);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        MemberVO currentUser = (session != null) ? (MemberVO) session.getAttribute("member") : null;

        if (currentUser == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            return;
        }

        String boardIdStr = req.getParameter("boardId");
        String content = req.getParameter("content");
        System.out.println("boardId: " + boardIdStr + ", content: " + content);

        if (boardIdStr == null || content == null || content.trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            return;
        }
        System.out.println("AAAAAAAAAAAAA");

        int boardId = Integer.parseInt(boardIdStr);
        CommentVO comment = new CommentVO(boardId, currentUser.getMember_id(), content);

        try {
            boardDAO.addComment(comment); // 댓글 추가
            boardDAO.incrementCommentsCount(boardId);
            resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
