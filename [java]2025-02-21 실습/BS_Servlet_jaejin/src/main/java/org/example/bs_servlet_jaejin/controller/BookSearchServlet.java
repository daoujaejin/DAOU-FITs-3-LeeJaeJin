package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
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
import java.util.List;

@WebServlet(value = "/bookSearch")
public class BookSearchServlet extends HttpServlet {

    SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final BookDAO bookDAO = new BookDAO(factory);

    public void BookSearch() {
        System.out.println("생성자 호출");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("init 호출");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String bookName = req.getParameter("title");
        String price = req.getParameter("price");
        System.out.println("책 이름: " + bookName + " 가격: " + price);

        List<BookVO> bookList = bookDAO.getBooks(bookName, price);
        req.setAttribute("bookList", bookList);
//        System.out.println("bookList: " + bookList);

//        // 1. 데이터 전달 통로를 열어서 결과데이터를 전달해야 한다.
//        //    전달되는 데이터의 형태를 먼저 지정해야 한다.
        resp.setContentType("text/html;charset=utf-8");
//        // 2. 결과를 돌려주기 위한 데이터 통로를 하나 열어야 한다. (PrintWriter)
        PrintWriter out = resp.getWriter();
//        // 3. 이렇게 열린 통로로 데이터를 전송한다.
        out.println("<html>");
        out.println("<head></head>");
        out.println("<body>");
//        for (BookVO book : bookList) {
//            out.println(book.getBtitle() + ", " + book.getBprice() + "<br>");
//        }
        out.println("<h1>검색 결과</h1>");

        // 테이블 생성
        out.println("<table border='1'>");
        out.println("<thead><tr><th>ISBN</th><th>제목</th><th>가격</th><th>저자</th></tr></thead>");
        out.println("<tbody>");

        for (BookVO book : bookList) {
            // 각 행에 클릭 이벤트 추가 (ISBN을 기반으로 요청)
            out.println("<tr onclick=\"location.href='/BS_Servlet_jaejin_war_exploded/bookDetail?isbn=" + book.getBisbn() + "'\">");
            out.println("<td>" + book.getBisbn() + "</td>");
            out.println("<td>" + book.getBtitle() + "</td>");
            out.println("<td>" + book.getBprice() + "</td>");
            out.println("<td>" + book.getBauthor() + "</td>");
            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");

        out.println("</body>");
        out.println("</html>");
        out.flush();
        out.close();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
