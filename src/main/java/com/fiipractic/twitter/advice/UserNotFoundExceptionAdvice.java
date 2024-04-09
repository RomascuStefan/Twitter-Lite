package com.fiipractic.twitter.advice;

import com.fiipractic.twitter.exception.UserAlreadyExistException;
import com.fiipractic.twitter.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class UserNotFoundExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    String UserNotFoundExceptionHandler(UserNotFoundException ex)
    {
        return  ex.getMessage();
    }
}
