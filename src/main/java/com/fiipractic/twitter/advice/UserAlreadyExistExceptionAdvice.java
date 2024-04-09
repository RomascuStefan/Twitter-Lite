package com.fiipractic.twitter.advice;

import com.fiipractic.twitter.exception.UserAlreadyExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class UserAlreadyExistExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(CONFLICT)
    String UserAlreadyExistHandler(UserAlreadyExistException ex)
    {
        return  ex.getMessage();
    }
}
