package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.dao.BoardDAO;
import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
import org.example.bs_servlet_jaejin.vo.BoardVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/editPost")
public class EditPostServlet extends HttpServlet {
    private final SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final BoardDAO boardDAO = new BoardDAO(factory);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 세션에서 로그인 사용자 확인
        HttpSession session = req.getSession(false);
        req.setCharacterEncoding("UTF-8");

        // 요청 파라미터에서 게시글 ID 가져오기
        String postId = req.getParameter("id");
        if (postId == null) {
            resp.sendRedirect("welcome");
            return;
        }

        // 게시글 정보 가져오기
        BoardVO post = boardDAO.getPostById(Integer.parseInt(postId));
        if (post == null) {
            resp.sendRedirect("welcome");
            return;
        }

        // 게시글 정보를 JSP로 전달
        req.setAttribute("post", post);
        req.getRequestDispatcher("editPost.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        req.setCharacterEncoding("UTF-8");
        // 요청 파라미터에서 게시글 정보 가져오기
        String postId = req.getParameter("id");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        System.out.println("postId: " + postId + ", title: " + title + ", content: " + content);

        if (postId == null || title == null || content == null) {
            resp.sendRedirect("welcome");
            return;
        }

        // 게시글 정보 업데이트
        BoardVO post = boardDAO.getPostById(Integer.parseInt(postId));
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            boardDAO.updatePost(post);
            resp.sendRedirect("welcome");
        } else {
            resp.sendRedirect("welcome");
        }
    }
}
