package com.zelenux.upprpo_server_test.repositories;

import com.zelenux.upprpo_server_test.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupsRepository extends JpaRepository<GroupEntity, Long>, JpaSpecificationExecutor<GroupEntity> {
    GroupEntity findByName(String name);
}