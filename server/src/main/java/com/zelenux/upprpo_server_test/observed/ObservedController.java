package com.zelenux.upprpo_server_test.observed;

import com.zelenux.upprpo_server_test.dataTransferObjects.Data;
import com.zelenux.upprpo_server_test.dataTransferObjects.Device;
import com.zelenux.upprpo_server_test.observed.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObservedController {
    private final ObservedDAO dao;
    private final ObservedResponder responder;

    public ObservedController(@Autowired ObservedDAO dao, @Autowired ObservedResponder responder) {
        this.dao = dao;
        this.responder = responder;
    }
    public String registerDevice(Device device) throws DeviceAlreadyExistsException, WrongFormatException {
        if (device.getName() == null || device.getPassword() == null){
            return formatError();
        }
        dao.addObserved(device);
        return responder.regSuccess();
    }
    public String addData(Data data) throws DeviceDoesNotExistException, WrongPasswordException, WrongFormatException {
        if (data.getDeviceName() == null || data.getDevicePassword() == null){
            return formatError();
        }
        dao.addData(data);
        return responder.dataAdded();
    }
    public String formatError() throws WrongFormatException {
        throw new WrongFormatException();
    }
}
