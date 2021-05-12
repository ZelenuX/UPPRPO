package com.zelenux.upprpo_server_test.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "OBSERVED_DEVICES")
public class ObservedDevices implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Long ID;

    @Column(name = "NAME", nullable = false)
    private String NAME;

    @Column(name = "PASSWORD", nullable = false)
    private String PASSWORD;

}
