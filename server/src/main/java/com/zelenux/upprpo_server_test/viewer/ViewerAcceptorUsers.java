package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.dataTransferObjects.User;
import com.zelenux.upprpo_server_test.viewer.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class ViewerAcceptorUsers {
    private ViewerController controller;

    public ViewerAcceptorUsers(@Autowired ViewerController controller) {
        this.controller = controller;
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody Map<String, String> model) {
        return "logged_in";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody Map<String, String> model) throws UserAlreadyExistsException, WrongFormatException {
        return controller.registerUser(new User(model.get("username"), model.get("password")));
    }
}
