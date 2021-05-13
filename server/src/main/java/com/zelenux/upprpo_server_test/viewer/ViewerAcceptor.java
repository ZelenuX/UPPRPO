package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.dataTransferObjects.User;
import com.zelenux.upprpo_server_test.viewer.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ViewerAcceptor {
    private ViewerController controller;

    public ViewerAcceptor(@Autowired ViewerController controller) {
        this.controller = controller;
    }

    @PostMapping("/register_user")
    public String registerUser(@RequestBody Map<String, String> model) throws UserAlreadyExistsException, WrongFormatException {
        return controller.registerUser(new User(model.get("name"), model.get("password")));
    }
}
