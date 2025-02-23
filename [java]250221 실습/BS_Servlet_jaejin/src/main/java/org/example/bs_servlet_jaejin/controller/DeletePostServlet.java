package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.dao.BoardDAO;
import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
import org.example.bs_servlet_jaejin.vo.BoardVO;
import org.example.bs_servlet_jaejin.vo.MemberVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/deletePost")
public class DeletePostServlet extends HttpServlet {
    private final SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final BoardDAO boardDAO = new BoardDAO(factory);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션에서 현재 로그인한 사용자 확인
        HttpSession session = req.getSession(false);
        MemberVO currentUser = (session != null) ? (MemberVO) session.getAttribute("member") : null;

        if (currentUser == null) {
            resp.sendRedirect("login.html");
            return;
        }

        // 요청 파라미터에서 게시글 ID 가져오기
        String postId = req.getParameter("id");
        if (postId == null) {
            resp.sendRedirect("welcome");
            return;
        }

        // 게시글 정보 가져오기
        BoardVO post = boardDAO.getPostById(Integer.parseInt(postId));

        // 작성자와 현재 유저가 같은지 확인
        if (post != null && post.getWriter_id().equals(currentUser.getMember_id())) {
            boardDAO.deletePost(Integer.parseInt(postId));  // 게시글 삭제
        }

        // 목록 페이지로 리다이렉트
        resp.sendRedirect("welcome");
    }
}
