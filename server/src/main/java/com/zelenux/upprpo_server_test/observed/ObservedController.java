package com.zelenux.upprpo_server_test.observed;

import com.zelenux.upprpo_server_test.observed.data_transfer_objects.Data;
import com.zelenux.upprpo_server_test.observed.data_transfer_objects.Device;
import com.zelenux.upprpo_server_test.observed.exceptions.AlreadyExistsException;
import com.zelenux.upprpo_server_test.observed.exceptions.DeviceDoesNotExistException;
import com.zelenux.upprpo_server_test.observed.exceptions.WrongPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObservedController {
    private ObservedDAO dao;
    private ObservedResponder responder;

    public ObservedController(@Autowired ObservedDAO dao, @Autowired ObservedResponder responder) {
        this.dao = dao;
        this.responder = responder;
    }
    public String registerDevice(Device device){
        if (device.getName() == null || device.getPassword() == null){
            return responder.regError();
        }
        try {
            dao.addObserved(device);
        } catch (AlreadyExistsException e) {
            return responder.regError();
        }
        return responder.regSuccess(device);
    }
    public String addData(Data data){
        if (data.getDeviceName() == null || data.getDevicePassword() == null){
            return responder.regError();
        }
        try {
            dao.addData(data);
        } catch (DeviceDoesNotExistException e) {
            return responder.deviceIsNotRegistered(data.getDeviceName());
        } catch (WrongPasswordException e) {
            return responder.wrongPassword(data.getDeviceName());
        }
        return responder.dataAdded();
    }
    public String formatError(){
        return responder.formatError();
    }
}
