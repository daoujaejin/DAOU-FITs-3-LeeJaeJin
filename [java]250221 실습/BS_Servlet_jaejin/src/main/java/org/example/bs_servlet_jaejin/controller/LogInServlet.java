package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.dao.BoardDAO;
import org.example.bs_servlet_jaejin.dao.BookDAO;
import org.example.bs_servlet_jaejin.dao.MemberDAO;
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
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/login")
public class LogInServlet extends HttpServlet {

    SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final MemberDAO memberDAO = new MemberDAO(factory);
    private final BoardDAO boardDAO = new BoardDAO(factory);

    public LogInServlet() {}

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String id = req.getParameter("id");
        String pw = req.getParameter("pw");

        MemberVO member = memberDAO.findUser(id, pw);

        if (member != null) {
            // 로그인 성공
            // WAS에게 session 저장공간 하나 주라고 요청
            HttpSession session = req.getSession();
            // req.getSession(false)
            // 기존에 할당된 session 객체가 이미 존재하면 가져다 주고,
            // 없으면 새로 만들지 말고 null을 리턴하세요!

            session.setAttribute("member", member);
//            req.setAttribute("member", member);

//            List<BoardVO> posts = boardDAO.getAllPosts();
//            req.setAttribute("posts", posts);
//            System.out.println("Posts: " + posts.size());

            // JSP를 이용해서 View 처리
//            RequestDispatcher rd = req.getRequestDispatcher("welcome.jsp");
//            rd.forward(req, resp);
            //resp.sendRedirect("welcome");
            resp.sendRedirect("loginSuccess");
            // setAttribute()를 이용해서 값을 저장할 수 있다.
            // getAttribute()로 다시 값을 꺼내올 수 있다.

            // invalidate() : 세션을 무효화한다.

        } else {
            // 로그인 실패
            resp.sendRedirect("loginError.html");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
