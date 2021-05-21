package com.zelenux.upprpo_server_test.dataTransferObjects;

import lombok.Getter;

@Getter
public class User {
    private Long id;
    private String name;
    private String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
    public User(Long id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }
}
