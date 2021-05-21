package com.zelenux.upprpo_server_test.dataTransferObjects;

import lombok.Getter;

@Getter
public class Device {
    private Long id;
    private String name;
    private String password;

    public Device(String name, String password){
        this.name = name;
        this.password = password;
    }
    public Device(Long id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
