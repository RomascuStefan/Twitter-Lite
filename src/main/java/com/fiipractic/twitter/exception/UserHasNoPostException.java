package com.fiipractic.twitter.exception;

public class UserHasNoPostException extends RuntimeException{
    public  UserHasNoPostException(String message){
        super((message));
    }
}
