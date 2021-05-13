package com.zelenux.upprpo_server_test.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "DEVICES_GROUPS")
public class DeviceGroupEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "DEVICE_ID", nullable = false)
    private DeviceEntity device;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private GroupEntity group;

    public DeviceGroupEntity() {}

    public DeviceGroupEntity(DeviceEntity device, GroupEntity group) {
        this.device = device;
        this.group = group;
    }
}
