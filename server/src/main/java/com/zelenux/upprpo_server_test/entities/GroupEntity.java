package com.zelenux.upprpo_server_test.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "GROUPS")
public class GroupEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ENTRANCE_PASSWORD", nullable = false)
    private String entrancePassword;

    public GroupEntity() {}

    public GroupEntity(String name, String entrancePassword) {
        this.name = name;
        this.entrancePassword = entrancePassword;
    }
}
