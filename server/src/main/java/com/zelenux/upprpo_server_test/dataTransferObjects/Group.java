package com.zelenux.upprpo_server_test.dataTransferObjects;

import lombok.Getter;

@Getter
public class Group {
    private String name;
    private String entrancePassword;

    public Group(String name, String entrancePassword) {
        this.name = name;
        this.entrancePassword = entrancePassword;
    }
}
