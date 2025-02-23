package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.dao.BoardDAO;
import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
import org.example.bs_servlet_jaejin.vo.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deleteComment")
public class DeleteCommentServlet extends HttpServlet {
    private final SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final BoardDAO boardDAO = new BoardDAO(factory);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        MemberVO currentUser = (session != null) ? (MemberVO) session.getAttribute("member") : null;

        req.setCharacterEncoding("UTF-8");
        if (currentUser == null) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            return;
        }

        String commentIdStr = req.getParameter("commentId");
        System.out.println("id: " + commentIdStr);

        if (commentIdStr == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            return;
        }

        int commentId = Integer.parseInt(commentIdStr);

        String boardIdStr = req.getParameter("boardId");
        int boardId = Integer.parseInt(boardIdStr);

        try {
            boardDAO.deleteComment(commentId); // 댓글 삭제
            boardDAO.decrementCommentsCount(boardId);
            System.out.println("댓글 삭제됨");
            resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }
}
