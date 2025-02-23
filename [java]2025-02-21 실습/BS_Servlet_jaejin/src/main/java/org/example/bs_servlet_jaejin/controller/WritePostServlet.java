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

@WebServlet("/writePost")
public class WritePostServlet extends HttpServlet {

    private final SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final BoardDAO boardDAO = new BoardDAO(factory);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        // 사용자 입력 데이터 가져오기
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        // 세션에서 로그인한 사용자 정보 가져오기
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("member") == null) {
            resp.sendRedirect("login.html");
            return;
        }

        String writerId = ((MemberVO) session.getAttribute("member")).getMember_id();
        System.out.println("title:" + title);
        System.out.println("content:" + content);
        System.out.println("name: " + writerId);

        // BoardVO 객체 생성 및 데이터 설정
        BoardVO board = new BoardVO();
        board.setTitle(title);
        board.setContent(content);
        board.setWriter_id(writerId);

        // DAO를 통해 게시글 삽입
        boardDAO.insertPost(board);

        // 게시판 페이지로 리다이렉트
        resp.sendRedirect("welcome");
    }
}
