package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.dataTransferObjects.*;
import com.zelenux.upprpo_server_test.utils.JSONBuilder;
import com.zelenux.upprpo_server_test.viewer.exceptions.ServerLogicException;
import com.zelenux.upprpo_server_test.viewer.exceptions.WrongFormatException;
import com.zelenux.upprpo_server_test.viewer.exceptions.deviceExceptions.DeviceException;
import com.zelenux.upprpo_server_test.viewer.exceptions.groupExceptions.GroupAlreadyExistsException;
import com.zelenux.upprpo_server_test.viewer.exceptions.groupExceptions.GroupException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserAlreadyExistsException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewerController {
    private ViewerDAO dao;
    private ViewerResponder responder;
    private JSONBuilder jsonBuilder = new JSONBuilder();

    public ViewerController(@Autowired ViewerDAO dao, @Autowired ViewerResponder responder) {
        this.dao = dao;
        this.responder = responder;
    }

    public void checkUsernameAndPassword(User user) throws UserException, WrongFormatException {
        if (user.getName() == null || user.getPassword() == null){
            formatError();
        }
        dao.checkUsernameAndPassword(user);
    }
    public String registerUser(User user) throws WrongFormatException, UserAlreadyExistsException {
        if (user.getName() == null || user.getPassword() == null){
            return formatError();
        }
        dao.addUser(user);
        return responder.userRegSuccess();
    }
    public String registerGroup(Group group) throws WrongFormatException, GroupAlreadyExistsException {
        if (group.getName() == null || group.getEntrancePassword() == null){
            formatError();
        }
        group = dao.addGroup(group);
        return jsonBuilder.startJSON().addInternals(group).finishJSON();
    }
    public String addUserToGroup(User user, Group group) throws GroupException, UserException, WrongFormatException {
        if (user.getName() == null || user.getPassword() == null
                || group.getName() == null || group.getEntrancePassword() == null){
            formatError();
        }
        group = dao.addUserToGroup(user, group);
        return jsonBuilder.startJSON().addInternals(group).finishJSON();
    }
    public String deleteUserFromGroup(User user, Group group) throws WrongFormatException, GroupException, UserException {
        if (user.getName() == null || user.getPassword() == null || group.getId() == null){
            formatError();
        }
        dao.removeUserFromGroup(user, group);
        return responder.userExitedFromGroup();
    }
    public String addDeviceToGroup(Device device, Group group, User user)
            throws WrongFormatException, GroupException, DeviceException, UserException {
        if (device.getName() == null || device.getPassword() == null
                || group.getId() == null || user.getName() == null || user.getPassword() == null){
            formatError();
        }
        DeviceSingleData deviceSingleData = dao.addDeviceToGroup(device, group, user);
        return jsonBuilder.startJSON().add("machine", deviceSingleData).finishJSON();
    }
    public String removeDeviceFromGroup(Device device, Group group, User user)
            throws WrongFormatException, GroupException, DeviceException, UserException {
        if (device.getId() == null || group.getId() == null || user.getName() == null || user.getPassword() == null){
            formatError();
        }
        dao.removeDeviceFromGroup(device, group, user);
        return responder.deviceRemovedFromGroup();
    }

    public String getGroupsWithUser(User user) throws WrongFormatException, UserException {
        if (user.getName() == null || user.getPassword() == null){
            return formatError();
        }
        List<Group> groups = dao.getGroupsWithUser(user);
        return jsonBuilder.startJSON().addArray("groups", groups.toArray(new Group[0])).finishJSON();
    }
    @Transactional
    public String getGroupInfo(User user, Group group) throws WrongFormatException, UserException, GroupException {
        if (user.getName() == null || user.getPassword() == null || group.getId() == null){
            formatError();
        }
        dao.checkUsernameAndPassword(user);
        group = dao.getGroupById(group.getId());
        List<Device> devices = dao.getGroupDevices(group);
        List<DeviceSingleData> devicesLastData = new ArrayList<>();
        devices.forEach(device -> {
            try {
                devicesLastData.add(new DeviceSingleData(device, dao.getLastDeviceData(device)));
            } catch (DeviceException e) {
                throw new ServerLogicException();
            }
        });
        return jsonBuilder.startJSON().add("name", group.getName())
                .addArray("observed", devicesLastData.toArray(new DeviceSingleData[0])).finishJSON();
    }
    @Transactional
    public String getDetailedDeviceInfo(User user, Group group, Device device)
            throws WrongFormatException, GroupException, DeviceException, UserException {
        if (user.getName() == null || user.getPassword() == null
                ||group.getId() == null || device.getId() == null){
            formatError();
        }
        List<Data> detailedData = dao.getDeviceDetailedData(device, group, user);
        Device deviceWithName = dao.getDeviceById(device.getId());
        return jsonBuilder.startJSON().add("name", deviceWithName.getName())
                .addArray("data", detailedData.toArray(new Data[0])).finishJSON();
    }

    public String formatError() throws WrongFormatException {
        throw new WrongFormatException();
    }
}
