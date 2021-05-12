package com.zelenux.upprpo_server_test.observed;

import com.zelenux.upprpo_server_test.observed.data_transfer_objects.Device;
import org.springframework.stereotype.Component;

@Component
public class ObservedResponder {
    public String regError(){
        return "<h1>Registration error</h1>";
    }
    public String regSuccess(Device device){
        return "<h1>Device \"" + device.getName() + "\" registered</h1>";
    }
    public String deviceIsNotRegistered(String deviceName){
        return "<h1>Device \"" + deviceName + "\" is not registered.</h1>";
    }
    public String wrongPassword(String deviceName){
        return "<h1>Wrong password for device \"" + deviceName + "\".</h1>";
    }
    public String dataAdded(){
        return "<h1>Data added.</h1>";
    }
    public String formatError(){
        return "<h1>Format error</h1>";
    }
}
