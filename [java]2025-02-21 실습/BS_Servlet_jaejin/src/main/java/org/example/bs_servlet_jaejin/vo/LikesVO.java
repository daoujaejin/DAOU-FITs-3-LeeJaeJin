package org.example.bs_servlet_jaejin.vo;

public class LikesVO {
    private String user_id;
    private int board_id;

    public LikesVO() {}

    public LikesVO(String user_id, int board_id) {
        this.user_id = user_id;
        this.board_id = board_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getBoard_id() {
        return board_id;
    }

    public void setBoard_id(int board_id) {
        this.board_id = board_id;
    }
}
