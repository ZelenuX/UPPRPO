package com.zelenux.upprpo_server_test.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "USERS_GROUPS")
public class UserGroupEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", nullable = false)
    private GroupEntity group;

    public UserGroupEntity() {}

    public UserGroupEntity(UserEntity user, GroupEntity group) {
        this.user = user;
        this.group = group;
    }
}
