package com.zelenux.upprpo_server_test.dataTransferObjects;

public class DataJSONDetailedAdder extends Data {
    public DataJSONDetailedAdder(Data data) {
        super(data);
    }

    @Override
    public StringBuilder addJSON(StringBuilder stringBuilder, boolean withBrackets) {
        if (withBrackets){
            stringBuilder.append("{");
        }
        stringBuilder.append("\"id\":").append(getId()).append(",\"timestamp\":\"").append(getTime()).append("\"");
        stringBuilder.append(",\"cpu_t\":");
        if (getProcessorTemperature() != null){
            stringBuilder.append(getProcessorTemperature());
        }
        else {
            stringBuilder.append(0);//todo change
        }
        stringBuilder.append(",\"cpu_load\":");
        if (getProcessorLoad() != null){
            stringBuilder.append(getProcessorLoad());
        }
        else {
            stringBuilder.append(0);//todo change
        }
        stringBuilder.append(",\"ram_load\":");
        if (getRamLoad() != null){
            stringBuilder.append(getRamLoad());
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
