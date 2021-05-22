package com.zelenux.upprpo_server_test.dataTransferObjects;

import com.zelenux.upprpo_server_test.utils.JSONAdder;

public class DeviceSingleData implements JSONAdder {
    private Device device;
    private Data data;

    public DeviceSingleData(Device device, Data data) {
        this.device = device;
        this.data = data;
    }

    @Override
    public StringBuilder addJSON(StringBuilder stringBuilder, boolean withBrackets) {
        if (withBrackets){
            stringBuilder.append("{");
        }
        stringBuilder.append("\"id\":").append(device.getId())
                .append(",\"name\":\"").append(device.getName()).append("\"");
        if (data != null){
            stringBuilder.append(",");
            data.addJSON(stringBuilder, false);
        }
        if (withBrackets){
            stringBuilder.append("}");
        }
        return stringBuilder;
    }
}
