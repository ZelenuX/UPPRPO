package com.zelenux.upprpo_server_test.viewer;

import com.zelenux.upprpo_server_test.dataTransferObjects.*;
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
import java.util.Optional;
import java.util.function.Supplier;

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

    public void checkUsernameAndPassword(User user) throws UserException {
        getUserIfPasswordCorrect(user);
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
    public Group addGroup(Group group) throws GroupAlreadyExistsException {
        GroupEntity groupEntity = groupsRepository.findByName(group.getName());
        if (groupEntity != null){
            throw new GroupAlreadyExistsException();
        }
        GroupEntity newGroupEntity = groupsRepository.save(new GroupEntity(group.getName(), group.getEntrancePassword()));
        return new Group(newGroupEntity);
    }

    public Group getGroupById(Long id) throws GroupDoesNotExistException {
        Optional<GroupEntity> groupOptional = groupsRepository.findById(id);
        if (groupOptional.isEmpty()){
            throw new GroupDoesNotExistException();
        }
        return new Group(groupOptional.get());
    }
    public Device getDeviceById(Long id) throws DeviceDoesNotExistException {
        return new Device(devicesRepository.findById(id).orElseThrow(DeviceDoesNotExistException::new));
    }

    @Transactional
    public Group addUserToGroup(User user, Group group) throws UserException, GroupException {
        UserEntity userEntity = getUserIfPasswordCorrect(user);
        GroupEntity groupEntity = getGroupIfPasswordCorrect(group);
        UserGroupEntity userGroupEntity = usersGroupsRepository
                .findByUserAndGroup(userEntity.getId(), groupEntity.getId());
        if (userGroupEntity != null){
            throw new GroupAlreadyHasUserException();
        }
        usersGroupsRepository.save(new UserGroupEntity(userEntity, groupEntity));
        return new Group(groupEntity);
    }

    @Transactional
    public void removeUserFromGroup(User user, Group group) throws UserException, GroupException {
        UserEntity userEntity = getUserIfPasswordCorrect(user);
        Optional<GroupEntity> groupEntityOptional = groupsRepository.findById(group.getId());
        if (groupEntityOptional.isEmpty()){
            throw new GroupDoesNotExistException();
        }
        GroupEntity groupEntity = groupEntityOptional.get();
        UserGroupEntity userGroupEntity = usersGroupsRepository
                .findByUserAndGroup(userEntity.getId(), groupEntity.getId());
        if (userGroupEntity == null){
            throw new GroupDoesNotContainUserException();
        }
        usersGroupsRepository.delete(userGroupEntity);
    }

    @Transactional
    public List<Group> getGroupsWithUser(User user) throws UserException {
        UserEntity userEntity = getUserIfPasswordCorrect(user);
        List<GroupEntity> groups = usersGroupsRepository.findByUser(userEntity.getId());
        List<Group> res = new ArrayList<>();
        groups.forEach(groupEntity -> res.add(new Group(groupEntity.getId(), groupEntity.getName(), groupEntity.getEntrancePassword())));
        return res;
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
    public List<Device> getGroupDevices(Group group) throws GroupException {
        Optional<GroupEntity> groupEntityOptional = groupsRepository.findById(group.getId());
        if (groupEntityOptional.isEmpty()){
            throw new GroupDoesNotExistException();
        }
        GroupEntity groupEntity = groupEntityOptional.get();
        List<DeviceGroupEntity> deviceGroupEntities = devicesGroupsRepository.findAllByGroupId(groupEntity.getId());
        List<Device> devices = new ArrayList<>();
        for (DeviceGroupEntity deviceGroupEntity : deviceGroupEntities){
            DeviceEntity deviceEntity = deviceGroupEntity.getDevice();
            devices.add(new Device(deviceEntity.getId(), deviceEntity.getName(), deviceEntity.getPassword()));
        }
        return devices;
    }

    @Transactional
    public List<Data> getAllDeviceData(Device device) throws DeviceException {
        Optional<DeviceEntity> deviceEntityOptional = devicesRepository.findById(device.getId());
        if (deviceEntityOptional.isEmpty()){
            throw new DeviceDoesNotExistException();
        }
        DeviceEntity deviceEntity = deviceEntityOptional.get();
        List<DataEntity> dataEntities = dataRepository.findAllByDeviceId(deviceEntity.getId());
        List<Data> dataList = new ArrayList<>();
        for (DataEntity dataEntity : dataEntities){
            dataList.add(new Data(dataEntity));
        }
        return dataList;
    }

    @Transactional
    public Data getLastDeviceData(Device device) throws DeviceException {
        Optional<DeviceEntity> deviceEntityOptional = devicesRepository.findById(device.getId());
        if (deviceEntityOptional.isEmpty()){
            throw new DeviceDoesNotExistException();
        }
        DeviceEntity deviceEntity = deviceEntityOptional.get();
        List<DataEntity> sortedDataEntities = dataRepository.findLastByDeviceId(deviceEntity.getId());
        if (sortedDataEntities.size() == 0){
            return null;
        }
        return new Data(sortedDataEntities.get(0));
    }

    @Transactional
    public DeviceSingleData addDeviceToGroup(Device device, Group group, User user) throws DeviceException, GroupException, UserException {
        DeviceEntity deviceEntity = getDeviceIfPasswordCorrect(device);
        UserEntity userEntity = getUserIfPasswordCorrect(user);
        Optional<GroupEntity> groupEntityOptional = groupsRepository.findById(group.getId());
        if (groupEntityOptional.isEmpty()){
            throw new GroupDoesNotExistException();
        }
        GroupEntity groupEntity = groupEntityOptional.get();
        if (usersGroupsRepository.findByUserAndGroup(userEntity.getId(), groupEntity.getId()) == null){
            throw new GroupDoesNotContainUserException();
        }
        if (devicesGroupsRepository.findByDeviceAndGroup(deviceEntity.getId(), groupEntity.getId()) != null){
            throw new GroupAlreadyHasDeviceException();
        }
        devicesGroupsRepository.save(new DeviceGroupEntity(deviceEntity, groupEntity));
        Device deviceWithId = new Device(deviceEntity);
        return new DeviceSingleData(deviceWithId, getLastDeviceData(deviceWithId));
    }

    @Transactional
    public void removeDeviceFromGroup(Device device, Group group, User user) throws DeviceException, GroupException, UserException {
        DeviceEntity deviceEntity = devicesRepository.findById(device.getId()).orElseThrow(DeviceDoesNotExistException::new);
        UserEntity userEntity = getUserIfPasswordCorrect(user);
        GroupEntity groupEntity = groupsRepository.findById(group.getId()).orElseThrow(GroupDoesNotExistException::new);
        if (usersGroupsRepository.findByUserAndGroup(userEntity.getId(), groupEntity.getId()) == null){
            throw new GroupDoesNotContainUserException();
        }
        DeviceGroupEntity deviceGroupEntity = devicesGroupsRepository.findByDeviceAndGroup(deviceEntity.getId(), groupEntity.getId());
        if (deviceGroupEntity == null){
            throw new GroupDoesNotContainDeviceException();
        }
        devicesGroupsRepository.delete(deviceGroupEntity);
    }

    @Transactional
    public List<Data> getDeviceDetailedData(Device device, Group group, User user) throws DeviceException, GroupException, UserException {
        DeviceEntity deviceEntity = devicesRepository.findById(device.getId()).orElseThrow(DeviceDoesNotExistException::new);
        UserEntity userEntity = getUserIfPasswordCorrect(user);
        GroupEntity groupEntity = groupsRepository.findById(group.getId()).orElseThrow(GroupDoesNotExistException::new);
        if (usersGroupsRepository.findByUserAndGroup(userEntity.getId(), groupEntity.getId()) == null){
            throw new GroupDoesNotContainUserException();
        }
        DeviceGroupEntity deviceGroupEntity = devicesGroupsRepository.findByDeviceAndGroup(deviceEntity.getId(), groupEntity.getId());
        if (deviceGroupEntity == null){
            throw new GroupDoesNotContainDeviceException();
        }
        List<Data> data = getAllDeviceData(device);
        for (int i = 0; i < data.size(); i++) {
            data.set(i, new DataJSONDetailedAdder(data.get(i)));
        }
        return data;
    }
}
