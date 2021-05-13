package com.zelenux.upprpo_server_test.repositories;

import com.zelenux.upprpo_server_test.entities.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DevicesRepository extends JpaRepository<DeviceEntity, Long>, JpaSpecificationExecutor<DeviceEntity> {
    DeviceEntity findByName(String name);
}