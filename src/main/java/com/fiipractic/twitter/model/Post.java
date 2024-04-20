package com.fiipractic.twitter.model;

import org.springframework.lang.NonNull;

import java.sql.Date;
import java.sql.Timestamp;

public class Post {
    @NonNull
    private int id;
    @NonNull
    private String user_id;
    @NonNull
    private String content;
    @NonNull
    private int like_count;
    @NonNull
    private Date time_post;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(@NonNull String user_id) {
        this.user_id = user_id;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    @NonNull
    public Date getTime_post() {
        return time_post;
    }

    public void setTime_post(@NonNull Date time_post) {
        this.time_post = time_post;
    }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user_id='" + user_id + '\'' +
                ", content='" + content + '\'' +
                ", like_count=" + like_count +
                ", time_post=" + time_post +
                '}';
    }
}
