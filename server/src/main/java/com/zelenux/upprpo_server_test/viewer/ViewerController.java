package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.dataTransferObjects.User;
import com.zelenux.upprpo_server_test.viewer.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewerController {
    private ViewerDAO dao;
    private ViewerResponder responder;

    public ViewerController(@Autowired ViewerDAO dao, @Autowired ViewerResponder responder) {
        this.dao = dao;
        this.responder = responder;
    }

    public String registerUser(User user) throws WrongFormatException, UserAlreadyExistsException {
        if (user.getName() == null || user.getPassword() == null){
            return formatError();
        }
        dao.addUser(user);
        return responder.userRegSuccess();
    }
    public String formatError() throws WrongFormatException {
        throw new WrongFormatException();
    }
}
