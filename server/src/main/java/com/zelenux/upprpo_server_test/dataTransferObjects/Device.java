package com.zelenux.upprpo_server_test.dataTransferObjects;

import lombok.Getter;

@Getter
public class Device {
    private String name;
    private String password;

    public Device(String name, String password){
        this.name = name;
        this.password = password;
    }
}
