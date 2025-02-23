package org.example.bs_servlet_jaejin.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.vo.BoardVO;
import org.example.bs_servlet_jaejin.vo.CommentVO;
import org.example.bs_servlet_jaejin.vo.LikesVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardDAO {
    private SqlSessionFactory sqlSessionFactory;

    public BoardDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    // 게시글 목록 조회 메서드
    public List<BoardVO> getAllPosts() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("BS_Servlet.Board.selectAllPosts");
        }
    }

    // 게시글 삽입 메서드
    public void insertPost(BoardVO board) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("BS_Servlet.Board.insertOne", board);
            session.commit(); // 트랜잭션 커밋
        }
    }

    public BoardVO getPostById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("BS_Servlet.Board.selectPostById", id);
        }
    }

    public void deletePost(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete("BS_Servlet.Board.deletePost", id);
            session.commit();
        }
    }

    public List<BoardVO> searchPostsByKeyword(String keyword) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("BS_Servlet.Board.searchPostsByKeyword", keyword);
        }
    }

    public void updatePost(BoardVO post) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("BS_Servlet.Board.updatePost", post);
            session.commit();
        }
    }

    public void addLike(LikesVO like) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("BS_Servlet.Board.addLike", like);
            session.commit();
        }
    }

    public void removeLike(LikesVO like) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete("BS_Servlet.Board.removeLike", like);
            session.commit();
        }
    }

    public boolean isLikedByUser(LikesVO like) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Integer count = session.selectOne("BS_Servlet.Board.isLikedByUser", like);
            return count != null && count > 0;
        }
    }

    public void incrementLikesCount(int boardId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("BS_Servlet.Board.incrementLikesCount", boardId);
            session.commit();
        }
    }

    public void decrementLikesCount(int boardId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("BS_Servlet.Board.decrementLikesCount", boardId);
            session.commit();
        }
    }

    public void addComment(CommentVO comment) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.insert("BS_Servlet.Board.addComment", comment);
            session.commit();
        }
    }

    public void deleteComment(int commentId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.delete("BS_Servlet.Board.deleteComment", commentId);
            session.commit();
        }
    }

    public List<CommentVO> getCommentsByBoardId(int boardId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectList("BS_Servlet.Board.getCommentsByBoardId", boardId);
        }
    }


    public void incrementCommentsCount(int boardId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("BS_Servlet.Board.incrementCommentsCount", boardId);
            session.commit();
        }
    }

    public void decrementCommentsCount(int boardId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("BS_Servlet.Board.decrementCommentsCount", boardId);
            session.commit();
        }
    }

    public void incrementViewCount(int boardId) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            session.update("BS_Servlet.Board.incrementViewCount", boardId);
            session.commit();
        }
    }
}
