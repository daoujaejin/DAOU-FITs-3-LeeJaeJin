package org.example.bs_servlet_jaejin.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.vo.BookVO;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BookDAO {
    private SqlSessionFactory sqlSessionFactory;

    public BookDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<BookVO> getBooks(String title, String price) {
        List<BookVO> list = null;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("title", title);
            if (Objects.equals(price, "1")) {
                params.put("maxPrice", 10000);
            } else if (Objects.equals(price, "2")) {
                params.put("maxPrice", 20000);
            } else if (Objects.equals(price, "3")) {
                params.put("maxPrice", 30000);
            } else if (Objects.equals(price, "4")) {
                params.put("maxPrice", 40000);
            }
            list = session.selectList("BS_Servlet.MyBook.selectBooksByNamePrice",
                    params);
            System.out.println(title);
            System.out.println(price);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public BookVO getBookByISBN(String isbn) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("BS_Servlet.MyBook.selectByISBN", isbn);
        }
    }

    public BookVO getBookById(int id) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.selectOne("BookMapper.selectBookById", id);
        }
    }

}
