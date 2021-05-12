package com.zelenux.upprpo_server_test.observed.repositories;

import com.zelenux.upprpo_server_test.observed.entities.ObservedDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ObservedDevicesRepository extends JpaRepository<ObservedDevice, Long>, JpaSpecificationExecutor<ObservedDevice> {
    ObservedDevice findByName(String name);
}