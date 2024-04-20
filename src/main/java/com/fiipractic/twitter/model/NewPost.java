package com.fiipractic.twitter.model;

import org.springframework.lang.NonNull;

public class NewPost {
    @NonNull
    private String credential;
    @NonNull
    private String content;

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

