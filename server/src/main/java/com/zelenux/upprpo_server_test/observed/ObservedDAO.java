package com.zelenux.upprpo_server_test.observed;

import com.zelenux.upprpo_server_test.observed.data_transfer_objects.Data;
import com.zelenux.upprpo_server_test.observed.data_transfer_objects.Device;
import com.zelenux.upprpo_server_test.observed.entities.ObservedData;
import com.zelenux.upprpo_server_test.observed.entities.ObservedDevice;
import com.zelenux.upprpo_server_test.observed.exceptions.AlreadyExistsException;
import com.zelenux.upprpo_server_test.observed.exceptions.DeviceDoesNotExistException;
import com.zelenux.upprpo_server_test.observed.exceptions.WrongPasswordException;
import com.zelenux.upprpo_server_test.observed.repositories.ObservedDataRepository;
import com.zelenux.upprpo_server_test.observed.repositories.ObservedDevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObservedDAO {
    private ObservedDevicesRepository devicesRepository;
    private ObservedDataRepository dataRepository;

    public ObservedDAO(@Autowired ObservedDevicesRepository devicesRepository,
                       @Autowired ObservedDataRepository dataRepository){
        this.devicesRepository = devicesRepository;
        this.dataRepository = dataRepository;
    }
    @Transactional
    public void addObserved(Device device) throws AlreadyExistsException {
        ObservedDevice deviceInDatabase = devicesRepository.findByName(device.getName());
        if (deviceInDatabase != null){
            throw new AlreadyExistsException();
        }
        devicesRepository.save(new ObservedDevice(device.getName(), device.getPassword()));
    }
    @Transactional
    public void addData(Data data) throws DeviceDoesNotExistException, WrongPasswordException {
        ObservedDevice deviceInDatabase = devicesRepository.findByName(data.getDeviceName());
        if (deviceInDatabase == null){
            throw new DeviceDoesNotExistException();
        }
        if (!deviceInDatabase.getPassword().equals(data.getDevicePassword())){
            throw new WrongPasswordException();
        }
        dataRepository.save(new ObservedData(deviceInDatabase,
                data.getProcessorTemperature(), data.getProcessorLoad(), data.getRamLoad()));
    }
}
