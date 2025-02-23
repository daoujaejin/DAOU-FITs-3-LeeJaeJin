package org.example.bs_servlet_jaejin.dao;

import javafx.scene.Parent;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.bs_servlet_jaejin.vo.MemberVO;

import java.util.HashMap;
import java.util.Map;

public class MemberDAO {
    private SqlSessionFactory sqlSessionFactory;

    public MemberDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public MemberVO findUser(String id, String pw) {
        MemberVO findVO = null;
        System.out.println("id: " + id + " pw: " + pw);
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            params.put("pw", pw);
            findVO = session.selectOne("BS_Servlet.Member.selectUserByIDPW", params);
//            System.out.println("name" + findVO.getMember_name());
//            System.out.println("id" + findVO.getMember_id());
//            System.out.println("pw" + findVO.getMember_pw());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return findVO;
    }
}
