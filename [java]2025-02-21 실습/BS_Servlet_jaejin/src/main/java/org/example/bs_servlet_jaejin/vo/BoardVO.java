package org.example.bs_servlet_jaejin.vo;

import java.sql.Timestamp;

public class BoardVO {
    private int board_id;       // 게시글 ID
    private String title;      // 제목
    private String content;    // 내용
    private String writer_id;   // 작성자 ID
    private Timestamp created_at; // 작성일
    private int comment_count;
    private int like_count;
    private int view_count;
    public BoardVO() {}

    public BoardVO(int board_id, String title, String content, String writer_id, Timestamp created_at, int comment_count, int like_count, int view_count) {
        this.board_id = board_id;
        this.title = title;
        this.content = content;
        this.writer_id = writer_id;
        this.created_at = created_at;
        this.comment_count = comment_count;
        this.like_count = like_count;
        this.view_count = view_count;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter_id() {
        return writer_id;
    }

    public void setWriter_id(String writer_id) {
        this.writer_id = writer_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }
}
