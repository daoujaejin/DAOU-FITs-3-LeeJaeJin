package org.example.bs_servlet_jaejin.controller;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.dao.BoardDAO;
import org.example.bs_servlet_jaejin.mybatis.MyBatisSessionFactory;
import org.example.bs_servlet_jaejin.vo.BoardVO;
import org.example.bs_servlet_jaejin.vo.CommentVO;
import org.example.bs_servlet_jaejin.vo.LikesVO;
import org.example.bs_servlet_jaejin.vo.MemberVO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/postDetail")
public class PostDetailServlet extends HttpServlet {
    private final SqlSessionFactory factory = MyBatisSessionFactory.getSqlSessionFactory();
    private final BoardDAO boardDAO = new BoardDAO(factory);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 게시글 ID 가져오기
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

        boardDAO.incrementViewCount(Integer.parseInt(postId));

        // 세션에서 현재 로그인한 사용자 정보 확인
        HttpSession session = req.getSession(false);
        MemberVO currentUser = (session != null) ? (MemberVO) session.getAttribute("member") : null;

        // 좋아요 여부 확인
        boolean isLikedByCurrentUser = false;
        if (currentUser != null) {
            LikesVO like = new LikesVO(currentUser.getMember_id(), post.getBoard_id());
            System.out.println("member_id: " + currentUser.getMember_id() + ", board_id: " + post.getBoard_id());
            isLikedByCurrentUser = boardDAO.isLikedByUser(like); // 좋아요 여부 확인
        }

        // 댓글 정보 가져오기
        int boardId = Integer.parseInt(postId);
        List<CommentVO> comments = boardDAO.getCommentsByBoardId(boardId);
        if (comments == null) {
            comments = new ArrayList<>(); // 댓글이 없으면 빈 리스트로 초기화
        }

        // JSP로 데이터 전달
        req.setAttribute("post", post);
        req.setAttribute("currentUser", currentUser);
        req.setAttribute("isLikedByCurrentUser", isLikedByCurrentUser);
        req.setAttribute("comments", comments);

        RequestDispatcher dispatcher = req.getRequestDispatcher("postDetail.jsp");
        dispatcher.forward(req, resp);
    }
}
