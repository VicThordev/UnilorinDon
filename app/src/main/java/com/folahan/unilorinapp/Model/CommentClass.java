package com.folahan.unilorinapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "comment_class")
public class CommentClass implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int likeNo;

    public int getLikeNo() {
        return likeNo;
    }

    public void setLikeNo(int likeNo) {
        this.likeNo = likeNo;
    }

    public int getCommentNos() {
        return commentNos;
    }

    public void setCommentNos(int commentNos) {
        this.commentNos = commentNos;
    }

    private String name;
    private String comment;
    private String like;
    private String commentNo;
    private int commentNos;

    public CommentClass(String name, String comment, String like, String commentNo) {
        this.name = name;
        this.comment = comment;
        this.like = like;
        this.commentNo = commentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getCommentNo() {
        return commentNo;
    }

    public void setCommentNo(String commentNo) {
        this.commentNo = commentNo;
    }
}
