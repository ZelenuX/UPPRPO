package com.zelenux.upprpo_server_test.dataTransferObjects;

import com.zelenux.upprpo_server_test.entities.DataEntity;
import com.zelenux.upprpo_server_test.utils.JSONAdder;
import lombok.Getter;

import java.util.Date;

@Getter
public class Data implements JSONAdder {
    private Long id;
    private String deviceName;
    private String devicePassword;
    private Integer processorTemperature;
    private Integer processorLoad;
    private Integer ramLoad;
    private Date time;

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
        time = dataEntity.getTime();
    }
    public Data(Data another){
        id = another.id;
        deviceName = another.deviceName;
        devicePassword = another.devicePassword;
        processorTemperature = another.processorTemperature;
        processorLoad = another.processorLoad;
        ramLoad = another.ramLoad;
        time = another.getTime();
    }

    @Override
    public StringBuilder addJSON(StringBuilder stringBuilder, boolean withBrackets) {
        if (withBrackets){
            stringBuilder.append("{");
        }
        stringBuilder.append("\"cpu_t\":");
        if (processorTemperature != null){
            stringBuilder.append(processorTemperature);
        }
        else {
            stringBuilder.append(0);//todo change
        }
        stringBuilder.append(",\"cpu_load\":");
        if (processorLoad != null){
            stringBuilder.append(processorLoad);
        }
        else {
            stringBuilder.append(0);//todo change
        }
        stringBuilder.append(",\"ram_load\":");
        if (ramLoad != null){
            stringBuilder.append(ramLoad);
        }
        else {
            stringBuilder.append(0);//todo change
        }
        if (withBrackets){
            stringBuilder.append("}");
        }
        return stringBuilder;
    }
}
