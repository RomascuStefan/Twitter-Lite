package com.fiipractic.twitter.advice;

import com.fiipractic.twitter.exception.UserHasNoPostException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class UserHasNoPostsExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(UserHasNoPostException.class)
    @ResponseStatus(NOT_FOUND)
        String UserHasNoPostExceptionHandler(UserHasNoPostException ex)
        {
            return  ex.getMessage();
        }
}
