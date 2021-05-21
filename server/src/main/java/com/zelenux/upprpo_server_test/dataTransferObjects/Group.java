package com.zelenux.upprpo_server_test.dataTransferObjects;

import com.zelenux.upprpo_server_test.entities.GroupEntity;
import com.zelenux.upprpo_server_test.utils.JSONAdder;
import lombok.Data;

@Data
public class Group implements JSONAdder {
    private Long id;
    private String name;
    private String entrancePassword;

    public Group(Long id){
        this.id = id;
    }
    public Group(Long id, String name){
        this.id = id;
        this.name = name;
    }
    public Group(String name, String entrancePassword) {
        this.name = name;
        this.entrancePassword = entrancePassword;
    }
    public Group(Long id, String name, String entrancePassword) {
        this.id = id;
        this.name = name;
        this.entrancePassword = entrancePassword;
    }
    public Group(GroupEntity groupEntity){
        id = groupEntity.getId();
        name = groupEntity.getName();
        entrancePassword = groupEntity.getEntrancePassword();
    }

    @Override
    public StringBuilder addJSON(StringBuilder stringBuilder, boolean withBrackets) {
        if (withBrackets){
            stringBuilder.append("{");
        }
        stringBuilder.append("\"id\":").append(id)
                .append(",\"name\":\"").append(name).append("\"");
        if (withBrackets){
            stringBuilder.append("}");
        }
        return stringBuilder;
    }
}
