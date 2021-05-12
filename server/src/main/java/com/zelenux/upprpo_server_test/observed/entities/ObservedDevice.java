package com.zelenux.upprpo_server_test.observed.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "OBSERVED_DEVICES")
public class ObservedDevice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    public ObservedDevice() {}
    public ObservedDevice(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
