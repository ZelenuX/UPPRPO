package com.zelenux.upprpo_server_test.dataTransferObjects;

import lombok.Getter;

@Getter
public class User {
    private String name;
    private String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
}
