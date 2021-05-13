package com.zelenux.upprpo_server_test.observed;

import com.zelenux.upprpo_server_test.observed.exceptions.DeviceAlreadyExistsException;
import com.zelenux.upprpo_server_test.observed.exceptions.DeviceDoesNotExistException;
import com.zelenux.upprpo_server_test.observed.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.observed.exceptions.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@ResponseBody
public class ObservedResponder {
    public String regSuccess(){
        return "reg_success";
    }
    public String dataAdded(){
        return "data_accepted";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DeviceAlreadyExistsException.class)
    public String alreadyRegistered(){
        return "device_name_already_exists";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DeviceDoesNotExistException.class)
    public String deviceIsNotRegistered(){
        return "unknown_device";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(WrongPasswordException.class)
    public String wrongPassword(){
        return "wrong_password";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ WrongFormatException.class, HttpMessageNotReadableException.class })
    public String formatError(){
        return "format_error";
    }
}
