package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.dataTransferObjects.Data;
import com.zelenux.upprpo_server_test.dataTransferObjects.Device;
import com.zelenux.upprpo_server_test.dataTransferObjects.Group;
import com.zelenux.upprpo_server_test.dataTransferObjects.User;
import com.zelenux.upprpo_server_test.entities.*;
import com.zelenux.upprpo_server_test.repositories.*;
import com.zelenux.upprpo_server_test.viewer.exceptions.NoDataException;
import com.zelenux.upprpo_server_test.viewer.exceptions.deviceExceptions.DeviceDoesNotExistException;
import com.zelenux.upprpo_server_test.viewer.exceptions.deviceExceptions.DeviceException;
import com.zelenux.upprpo_server_test.viewer.exceptions.deviceExceptions.WrongDevicePasswordException;
import com.zelenux.upprpo_server_test.viewer.exceptions.groupExceptions.*;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserAlreadyExistsException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserDoesNotExistException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.UserException;
import com.zelenux.upprpo_server_test.viewer.exceptions.userExceptions.WrongUserPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewerDAO {
    private UsersRepository usersRepository;
    private GroupsRepository groupsRepository;
    private DevicesRepository devicesRepository;
    private DataRepository dataRepository;
    private UsersGroupsRepository usersGroupsRepository;
    private DevicesGroupsRepository devicesGroupsRepository;

    private UserEntity getUserIfPasswordCorrect(User user) throws UserException {
        UserEntity userEntity = usersRepository.findByName(user.getName());
        if (userEntity == null){
            throw new UserDoesNotExistException();
        }
        if (!userEntity.getPassword().equals(user.getPassword())){
            throw new WrongUserPasswordException();
        }
        return userEntity;
    }
    private GroupEntity getGroupIfPasswordCorrect(Group group) throws GroupException {
        GroupEntity groupEntity = groupsRepository.findByName(group.getName());
        if (groupEntity == null){
            throw new GroupDoesNotExistException();
        }
        if (!groupEntity.getEntrancePassword().equals(group.getEntrancePassword())){
            throw new WrongGroupPasswordException();
        }
        return groupEntity;
    }
    private DeviceEntity getDeviceIfPasswordCorrect(Device device) throws DeviceException {
        DeviceEntity deviceEntity = devicesRepository.findByName(device.getName());
        if (deviceEntity == null){
            throw new DeviceDoesNotExistException();
        }
        if (!deviceEntity.getPassword().equals(device.getPassword())){
            throw new WrongDevicePasswordException();
        }
        return deviceEntity;
    }

    public ViewerDAO(@Autowired UsersRepository usersRepository,
                     @Autowired GroupsRepository groupsRepository,
                     @Autowired DevicesRepository devicesRepository,
                     @Autowired DataRepository dataRepository,
                     @Autowired UsersGroupsRepository usersGroupsRepository,
                     @Autowired DevicesGroupsRepository devicesGroupsRepository) {
        this.usersRepository = usersRepository;
        this.groupsRepository = groupsRepository;
        this.devicesRepository = devicesRepository;
        this.dataRepository = dataRepository;
        this.usersGroupsRepository = usersGroupsRepository;
        this.devicesGroupsRepository = devicesGroupsRepository;
    }

    @Transactional
    public void addUser(User user) throws UserAlreadyExistsException {
        UserEntity userEntity = usersRepository.findByName(user.getName());
        if (userEntity != null){
            throw new UserAlreadyExistsException();
        }
        usersRepository.save(new UserEntity(user.getName(), user.getPassword()));
    }

    @Transactional
    public void addGroup(Group group) throws GroupAlreadyExistsException {
        GroupEntity groupEntity = groupsRepository.findByName(group.getName());
        if (groupEntity != null){
            throw new GroupAlreadyExistsException();
        }
        groupsRepository.save(new GroupEntity(group.getName(), group.getEntrancePassword()));
    }

    @Transactional
    public void addUserToGroup(User user, Group group) throws UserException, GroupException {
        UserEntity userEntity = getUserIfPasswordCorrect(user);
        GroupEntity groupEntity = getGroupIfPasswordCorrect(group);
        UserGroupEntity userGroupEntity = usersGroupsRepository
                .findByUserAndGroup(userEntity.getId(), groupEntity.getId());
        if (userGroupEntity != null){
            throw new GroupAlreadyHasUserException();
        }
        usersGroupsRepository.save(new UserGroupEntity(userEntity, groupEntity));
    }

    @Transactional
    public void addDeviceToGroup(Device device, Group group) throws DeviceException, GroupException {
        DeviceEntity deviceEntity = getDeviceIfPasswordCorrect(device);
        GroupEntity groupEntity = getGroupIfPasswordCorrect(group);
        DeviceGroupEntity deviceGroupEntity = devicesGroupsRepository
                .findByDeviceAndGroup(deviceEntity.getId(), groupEntity.getId());
        if (deviceGroupEntity != null){
            throw new GroupAlreadyHasDeviceException();
        }
        devicesGroupsRepository.save(new DeviceGroupEntity(deviceEntity, groupEntity));
    }

    @Transactional
    public List<Device> getDevicesOfGroup(Group group) throws GroupException {
        GroupEntity groupEntity = getGroupIfPasswordCorrect(group);
        List<DeviceGroupEntity> deviceGroupEntities = devicesGroupsRepository.findAllByGroupId(groupEntity.getId());
        List<Device> devices = new ArrayList<>();
        for (DeviceGroupEntity deviceGroupEntity : deviceGroupEntities){
            DeviceEntity deviceEntity = deviceGroupEntity.getDevice();
            devices.add(new Device(deviceEntity.getName(), deviceEntity.getPassword()));
        }
        return devices;
    }

    @Transactional
    public List<Data> getAllData(Device device) throws DeviceException, NoDataException {
        DeviceEntity deviceEntity = getDeviceIfPasswordCorrect(device);
        List<DataEntity> dataEntities = dataRepository.findAllByDeviceId(deviceEntity.getId());
        List<Data> dataList = new ArrayList<>();
        if (dataEntities == null){
            throw new NoDataException();
        }
        for (DataEntity dataEntity : dataEntities){
            dataList.add(new Data(device.getName(), device.getPassword(),
                    dataEntity.getProcessorTemperature(), dataEntity.getProcessorLoad(), dataEntity.getRamLoad()));
        }
        return dataList;
    }
}
