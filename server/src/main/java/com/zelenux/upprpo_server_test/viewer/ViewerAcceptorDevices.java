package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.dataTransferObjects.Device;
import com.zelenux.upprpo_server_test.dataTransferObjects.Group;
import com.zelenux.upprpo_server_test.dataTransferObjects.User;
import com.zelenux.upprpo_server_test.viewer.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.viewer.exceptions.deviceExceptions.DeviceException;
import com.zelenux.upprpo_server_test.viewer.exceptions.groupExceptions.GroupException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/observed")
public class ViewerAcceptorDevices {
    private ViewerController controller;

    public ViewerAcceptorDevices(@Autowired ViewerController controller) {
        this.controller = controller;
    }

    @PostMapping("/id/{id}")
    public String loginUser(@PathVariable(value="id") Long deviceId, @RequestBody Map<String, Object> model)
            throws WrongFormatException, GroupException, DeviceException, UserException {
        try {
            Map<String, String> credentials = (Map<String, String>) model.get("credentials");
            if (credentials == null){
                return controller.formatError();
            }
            User user = new User(credentials.get("username"), credentials.get("password"));
            Device device = new Device(deviceId);
            Integer groupId = (Integer) model.get("group_id");
            if (groupId == null){
                return controller.formatError();
            }
            Group group = new Group((long)groupId);
            return controller.getDetailedDeviceInfo(user, group, device);
        }
        catch (ClassCastException e){
            return controller.formatError();
        }
    }
}
