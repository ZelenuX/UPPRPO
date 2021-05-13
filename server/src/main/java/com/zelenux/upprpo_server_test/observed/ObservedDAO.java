package com.zelenux.upprpo_server_test.observed;

import com.zelenux.upprpo_server_test.dataTransferObjects.Data;
import com.zelenux.upprpo_server_test.dataTransferObjects.Device;
import com.zelenux.upprpo_server_test.entities.DataEntity;
import com.zelenux.upprpo_server_test.entities.DeviceEntity;
import com.zelenux.upprpo_server_test.observed.exceptions.DeviceAlreadyExistsException;
import com.zelenux.upprpo_server_test.observed.exceptions.DeviceDoesNotExistException;
import com.zelenux.upprpo_server_test.observed.exceptions.WrongPasswordException;
import com.zelenux.upprpo_server_test.repositories.DataRepository;
import com.zelenux.upprpo_server_test.repositories.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObservedDAO {
    private DevicesRepository devicesRepository;
    private DataRepository dataRepository;

    public ObservedDAO(@Autowired DevicesRepository devicesRepository,
                       @Autowired DataRepository dataRepository){
        this.devicesRepository = devicesRepository;
        this.dataRepository = dataRepository;
    }
    @Transactional
    public void addObserved(Device device) throws DeviceAlreadyExistsException {
        DeviceEntity deviceInDatabase = devicesRepository.findByName(device.getName());
        if (deviceInDatabase != null){
            throw new DeviceAlreadyExistsException();
        }
        devicesRepository.save(new DeviceEntity(device.getName(), device.getPassword()));
    }
    @Transactional
    public void addData(Data data) throws DeviceDoesNotExistException, WrongPasswordException {
        DeviceEntity deviceInDatabase = devicesRepository.findByName(data.getDeviceName());
        if (deviceInDatabase == null){
            throw new DeviceDoesNotExistException();
        }
        if (!deviceInDatabase.getPassword().equals(data.getDevicePassword())){
            throw new WrongPasswordException();
        }
        dataRepository.save(new DataEntity(deviceInDatabase,
                data.getProcessorTemperature(), data.getProcessorLoad(), data.getRamLoad()));
    }
}
