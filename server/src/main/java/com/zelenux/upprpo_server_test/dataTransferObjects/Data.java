package com.zelenux.upprpo_server_test.dataTransferObjects;

import lombok.Getter;

@Getter
public class Data {
    private String deviceName;
    private String devicePassword;
    private Integer processorTemperature;
    private Integer processorLoad;
    private Integer ramLoad;

    public Data(String deviceName, String devicePassword, Integer processorTemperature, Integer processorLoad, Integer ramLoad){
        this.deviceName = deviceName;
        this.devicePassword = devicePassword;
        this.processorTemperature = processorTemperature;
        this.processorLoad = processorLoad;
        this.ramLoad = ramLoad;
    }
}
