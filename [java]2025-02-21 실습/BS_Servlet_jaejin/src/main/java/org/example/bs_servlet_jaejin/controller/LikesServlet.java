package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.dao.BoardDAO;
import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
import org.example.bs_servlet_jaejin.vo.LikesVO;
import org.example.bs_servlet_jaejin.vo.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/likePost")
public class LikesServlet extends HttpServlet {
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
        String action = req.getParameter("action");

        if (boardIdStr == null || action == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
            return;
        }

        int boardId = Integer.parseInt(boardIdStr);
        LikesVO like = new LikesVO(currentUser.getMember_id(), boardId);

        if ("like".equals(action)) {
            boardDAO.addLike(like); // 좋아요 추가
            boardDAO.incrementLikesCount(boardId);
        } else if ("unlike".equals(action)) {
            boardDAO.removeLike(like); // 좋아요 취소
            boardDAO.decrementLikesCount(boardId);
        }

        resp.setStatus(HttpServletResponse.SC_OK); // 200 OK
    }
}
