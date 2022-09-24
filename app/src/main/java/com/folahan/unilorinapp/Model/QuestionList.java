package com.folahan.unilorinapp.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "QuestionList")
public class QuestionList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int comment;
    private String id;
    private String name;
    private String question;
    private int like;

    public QuestionList(String name, String id, String question, int comment, int like) {
        this.name = name;
        this.id = id;
        this.question = question;
        this.comment = comment;
        this.like = like;
    }

    public  QuestionList() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
