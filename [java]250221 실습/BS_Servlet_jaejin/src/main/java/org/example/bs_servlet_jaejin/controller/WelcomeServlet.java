package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.dao.BoardDAO;
import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
import org.example.bs_servlet_jaejin.vo.BoardVO;
import org.example.bs_servlet_jaejin.vo.MemberVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    private final SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final BoardDAO boardDAO = new BoardDAO(factory);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션 확인
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("member") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String keyword = req.getParameter("keyword");

        // 게시글 목록 가져오기
        List<BoardVO> posts;
        if (keyword != null && !keyword.trim().isEmpty()) {
            posts = boardDAO.searchPostsByKeyword(keyword); // 검색 메서드 호출
        } else {
            posts = boardDAO.getAllPosts(); // 전체 게시글 가져오기
        }

        req.setAttribute("posts", posts);
        // JSP로 데이터 전달
        RequestDispatcher dispatcher = req.getRequestDispatcher("welcome.jsp");
        dispatcher.forward(req, resp);
    }
}
