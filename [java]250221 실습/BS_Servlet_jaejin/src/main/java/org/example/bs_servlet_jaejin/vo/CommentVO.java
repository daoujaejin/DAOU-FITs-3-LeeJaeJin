package org.example.bs_servlet_jaejin.vo;

import java.sql.Timestamp;

public class CommentVO {
    private int id;
    private int board_id;
    private String user_id;
    private String content;
    private Timestamp created_at;

    public CommentVO() {}

    public CommentVO(int id, int board_id, String user_id, String content, Timestamp created_at) {
        this.id = id;
        this.board_id = board_id;
        this.user_id = user_id;
        this.content = content;
        this.created_at = created_at;
    }

    public CommentVO(int boardId, String memberId, String content) {
        this.board_id = boardId;
        this.user_id = memberId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
