package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.viewer.exceptions.ServerLogicException;
import com.zelenux.upprpo_server_test.viewer.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.viewer.exceptions.deviceExceptions.DeviceDoesNotExistException;
import com.zelenux.upprpo_server_test.viewer.exceptions.groupExceptions.*;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserAlreadyExistsException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserDoesNotExistException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.WrongUserPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@ResponseBody
public class ViewerResponder {
    public String userRegSuccess(){
        return "user_registered";
    }
    public String userExitedFromGroup(){
        return "exited_from_group";
    }
    public String deviceAddedToGroup(){
        return "device_added";
    }
    public String deviceRemovedFromGroup(){
        return "device_removed";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public String userAlreadyRegistered(){
        return "user_already_exists";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(GroupAlreadyExistsException.class)
    public String groupAlreadyRegistered(){
        return "group_already_exists";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserDoesNotExistException.class)
    public String userDoesNotExist(){
        return "user_does_not_exist";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DeviceDoesNotExistException.class)
    public String deviceDoesNotExist(){
        return "device_does_not_exist";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(WrongUserPasswordException.class)
    public String wrongUserPassword(){
        return "wrong_user_password";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(GroupDoesNotExistException.class)
    public String groupDoesNotExist(){
        return "group_does_not_exist";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(WrongGroupPasswordException.class)
    public String wrongGroupPassword(){
        return "wrong_group_password";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(GroupAlreadyHasUserException.class)
    public String groupAlreadyHasUser(){
        return "group_already_contains_user";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(GroupAlreadyHasDeviceException.class)
    public String groupAlreadyHasDevice(){
        return "group_already_contains_device";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(GroupDoesNotContainUserException.class)
    public String groupDoesNotContainUser(){
        return "group_does_not_contain_user";
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(GroupDoesNotContainDeviceException.class)
    public String groupDoesNotContainDevice(){
        return "group_does_not_contain_device";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ WrongFormatException.class, HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class })
    public String formatError(){
        return "format_error";
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ServerLogicException.class)
    public String serverLogicError(){
        return "internal_server_logic_error";
    }
}
