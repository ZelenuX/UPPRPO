package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.viewer.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ViewerResponder {
    public String userRegSuccess(){
        return "user_registered";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public String alreadyRegistered(){
        return "user_already_exists";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ WrongFormatException.class, HttpMessageNotReadableException.class })
    public String formatError(){
        return "format_error";
    }
}
