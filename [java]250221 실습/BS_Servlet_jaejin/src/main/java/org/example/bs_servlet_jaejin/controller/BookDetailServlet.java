package org.example.bs_servlet_jaejin.controller;

import org.example.bs_servlet_jaejin.dao.BookDAO;
import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
import org.example.bs_servlet_jaejin.vo.BookVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//
//@WebServlet(value = "/bookDetail")
//public class BookDetailServlet extends HttpServlet {
//
//    private final BookDAO bookDAO = new BookDAO(MyBatisSessionFactory.getSqlSessionFactory());
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("utf-8");
//
//        // 클라이언트에서 전달된 ISBN 파라미터 받기
//        String isbn = req.getParameter("isbn");
//
//        // DAO를 통해 특정 책 정보 가져오기
//        BookVO book = bookDAO.getBookByISBN(isbn);
//
//        // 응답 데이터 설정
//        resp.setContentType("text/html;charset=utf-8");
//        PrintWriter out = resp.getWriter();
//
//        // HTML 시작
//        out.println("<!DOCTYPE html>");
//        out.println("<html lang='ko'>");
//        out.println("<head>");
//        out.println("<meta charset='UTF-8'>");
//        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
//        out.println("<title>책 상세 정보</title>");
//        out.println("</head>");
//        out.println("<body>");
//
//        if (book != null) {
//            // 책 정보 출력
//            out.println("<h1>책 상세 정보</h1>");
//            out.println("<p><strong>ISBN:</strong> " + book.getBisbn() + "</p>");
//            out.println("<p><strong>제목:</strong> " + book.getBtitle() + "</p>");
//            out.println("<p><strong>가격:</strong> " + book.getBprice() + "원</p>");
//            out.println("<p><strong>저자:</strong> " + book.getBauthor() + "</p>");
//        } else {
//            // 책 정보가 없을 경우 메시지 출력
//            out.println("<h1>책 정보를 찾을 수 없습니다.</h1>");
//        }
//
//        // HTML 끝
//        out.println("</body>");
//        out.println("</html>");
//
//        out.flush();
//    }
//}
//package org.example.bs_servlet_jaejin.controller;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.example.bs_servlet_jaejin.dao.BookDAO;
//import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
//import org.example.bs_servlet_jaejin.vo.BookVO;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;

//@WebServlet("/bookDetail")
//public class BookDetailServlet extends HttpServlet {
//    private final SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
//    private final BookDAO bookDAO = new BookDAO(factory);
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // 요청 파라미터에서 책 ID 가져오기
//        String bookIdParam = req.getParameter("id");
//
//        // 입력값 검증
//        if (bookIdParam == null || bookIdParam.trim().isEmpty()) {
//            req.setAttribute("errorMessage", "유효하지 않은 책 ID입니다.");
//            forwardToErrorPage(req, resp);
//            return;
//        }
//
//        try {
//            int bookId = Integer.parseInt(bookIdParam);
//
//            // 데이터베이스에서 책 정보 가져오기
//            BookVO book = bookDAO.getBookById(bookId);
//            if (book == null) {
//                req.setAttribute("errorMessage", "해당 책 정보를 찾을 수 없습니다.");
//                forwardToErrorPage(req, resp);
//                return;
//            }
//
//            // JSP로 데이터 전달
//            req.setAttribute("book", book);
//            RequestDispatcher dispatcher = req.getRequestDispatcher("bookDetail.jsp");
//            dispatcher.forward(req, resp);
//
//        } catch (NumberFormatException e) {
//            req.setAttribute("errorMessage", "책 ID는 숫자여야 합니다.");
//            forwardToErrorPage(req, resp);
//        }
//    }
//
//    private void forwardToErrorPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        RequestDispatcher dispatcher = req.getRequestDispatcher("error.jsp");
//        dispatcher.forward(req, resp);
//    }
//}
//package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.dao.BookDAO;
import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
import org.example.bs_servlet_jaejin.vo.BookVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/bookDetail")
public class BookDetailServlet extends HttpServlet {
    private final SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final BookDAO bookDAO = new BookDAO(factory);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 요청 파라미터에서 책 ID 가져오기
        String bookIdParam = req.getParameter("id");

        // 입력값 검증
        if (bookIdParam == null || bookIdParam.trim().isEmpty()) {
            req.setAttribute("errorMessage", "유효하지 않은 책 ID입니다.");
            forwardToErrorPage(req, resp);
            return;
        }

        try {
            int bookId = Integer.parseInt(bookIdParam);

            // 데이터베이스에서 책 정보 가져오기
            BookVO book = bookDAO.getBookById(bookId);
            if (book == null) {
                req.setAttribute("errorMessage", "해당 책 정보를 찾을 수 없습니다.");
                forwardToErrorPage(req, resp);
                return;
            }

            // JSP로 데이터 전달
            req.setAttribute("book", book);
            RequestDispatcher dispatcher = req.getRequestDispatcher("bookDetail.jsp");
            dispatcher.forward(req, resp);

        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "책 ID는 숫자여야 합니다.");
            forwardToErrorPage(req, resp);
        }
    }

    private void forwardToErrorPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("error.jsp");
        dispatcher.forward(req, resp);
    }
}
