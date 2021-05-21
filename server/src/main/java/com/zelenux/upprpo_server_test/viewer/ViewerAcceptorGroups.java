package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.dataTransferObjects.Device;
import com.zelenux.upprpo_server_test.dataTransferObjects.Group;
import com.zelenux.upprpo_server_test.dataTransferObjects.User;
import com.zelenux.upprpo_server_test.viewer.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.viewer.exceptions.groupExceptions.GroupAlreadyExistsException;
import com.zelenux.upprpo_server_test.viewer.exceptions.groupExceptions.GroupException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/group")
public class ViewerAcceptorGroups {
    private ViewerController controller;

    public ViewerAcceptorGroups(@Autowired ViewerController controller) {
        this.controller = controller;
    }

    @PostMapping("")//todo post -> get
    public String getGroupsOfUser(@RequestBody Map<String, Object> model) throws WrongFormatException, UserException {
        try {
            Map<String, String> credentials = (Map<String, String>) model.get("credentials");
            if (credentials == null){
                return controller.formatError();
            }
            User user = new User(credentials.get("username"), credentials.get("password"));
            return controller.getGroupsWithUser(user);
        }
        catch (ClassCastException e){
            return controller.formatError();
        }
    }

    @PostMapping("/create")
    public String registerGroup(@RequestBody Map<String, Object> model) throws WrongFormatException, UserException, GroupAlreadyExistsException {
        try {
            Map<String, String> credentials = (Map<String, String>) model.get("credentials");
            if (credentials == null){
                return controller.formatError();
            }
            User user = new User(credentials.get("username"), credentials.get("password"));
            controller.checkUsernameAndPassword(user);
            return controller.registerGroup(new Group((String)model.get("name"), (String)model.get("password")));
        }
        catch (ClassCastException e){
            return controller.formatError();
        }
    }

    @PostMapping("/join")
    public String addUserToGroup(@RequestBody Map<String, Object> model) throws WrongFormatException, GroupException, UserException {
        try {
            Map<String, String> credentials = (Map<String, String>) model.get("credentials");
            if (credentials == null){
                return controller.formatError();
            }
            User user = new User(credentials.get("username"), credentials.get("password"));
            return controller.addUserToGroup(user, new Group((String)model.get("name"), (String)model.get("password")));
        }
        catch (ClassCastException e){
            return controller.formatError();
        }
    }

    @PostMapping("/exit")
    public String exitGroup(@RequestBody Map<String, Object> model) throws WrongFormatException, GroupException, UserException {
        try {
            Integer groupId = (Integer)model.get("id");
            Map<String, String> credentials = (Map<String, String>) model.get("credentials");
            if (credentials == null || groupId == null){
                return controller.formatError();
            }
            User user = new User(credentials.get("username"), credentials.get("password"));
            return controller.deleteUserFromGroup(user, new Group((long)groupId));
        }
        catch (ClassCastException e){
            return controller.formatError();
        }
    }

    @PostMapping("/id/{id}")//todo post -> get
    public String getGroupInfo(@PathVariable(value="id") Long id, @RequestBody Map<String, Object> model)
            throws WrongFormatException, GroupException, UserException {
        try {
            Map<String, String> credentials = (Map<String, String>) model.get("credentials");
            if (credentials == null){
                return controller.formatError();
            }
            User user = new User(credentials.get("username"), credentials.get("password"));
            Group group = new Group(id);
            return controller.getGroupInfo(user, group);
        }
        catch (ClassCastException e){
            return controller.formatError();
        }
    }

    /*@PostMapping("/id/{id}/add")//todo post -> get
    public String addDeviceToGroup(@PathVariable(value="id") Long id, @RequestBody Map<String, Object> model)
            throws WrongFormatException {
        try {
            Map<String, String> credentials = (Map<String, String>) model.get("credentials");
            if (credentials == null) {
                return controller.formatError();
            }
            User user = new User(credentials.get("username"), credentials.get("password"));
            Device device = new Device((String) model.get("observed_name"), (String) model.get("observed_password"));
        }
        catch (ClassCastException e){
            return controller.formatError();
        }
    }*/
}
