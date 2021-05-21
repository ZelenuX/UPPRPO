package com.zelenux.upprpo_server_test.dataTransferObjects;

import com.zelenux.upprpo_server_test.entities.DataEntity;
import com.zelenux.upprpo_server_test.utils.JSONAdder;
import lombok.Getter;

@Getter
public class Data implements JSONAdder {
    private Long id;
    private String deviceName;
    private String devicePassword;
    private Integer processorTemperature;
    private Integer processorLoad;
    private Integer ramLoad;

    public Data(String deviceName, String devicePassword, Integer processorTemperature, Integer processorLoad, Integer ramLoad) {
        this.deviceName = deviceName;
        this.devicePassword = devicePassword;
        this.processorTemperature = processorTemperature;
        this.processorLoad = processorLoad;
        this.ramLoad = ramLoad;
    }
    public Data(Long id, String deviceName, String devicePassword, Integer processorTemperature, Integer processorLoad, Integer ramLoad){
        this.id = id;
        this.deviceName = deviceName;
        this.devicePassword = devicePassword;
        this.processorTemperature = processorTemperature;
        this.processorLoad = processorLoad;
        this.ramLoad = ramLoad;
    }
    public Data(DataEntity dataEntity){
        id = dataEntity.getId();
        deviceName = dataEntity.getDevice().getName();
        devicePassword = dataEntity.getDevice().getPassword();
        processorTemperature = dataEntity.getProcessorTemperature();
        processorLoad = dataEntity.getProcessorLoad();
        ramLoad = dataEntity.getRamLoad();
    }

    @Override
    public StringBuilder addJSON(StringBuilder stringBuilder, boolean withBrackets) {
        if (withBrackets){
            stringBuilder.append("{");
        }
        boolean firstAdded = false;
        if (processorTemperature != null){
            stringBuilder.append("\"cpu_t\":").append(processorTemperature);
            firstAdded = true;
        }
        if (processorLoad != null){
            if (firstAdded) {
                stringBuilder.append(",");
            }
            else {
                firstAdded = true;
            }
            stringBuilder.append("\"cpu_load\":").append(processorLoad);
        }
        if (ramLoad != null){
            if (firstAdded) {
                stringBuilder.append(",");
            }
            stringBuilder.append("\"ram_load\":").append(ramLoad);
        }
        if (withBrackets){
            stringBuilder.append("}");
        }
        return stringBuilder;
    }
}
