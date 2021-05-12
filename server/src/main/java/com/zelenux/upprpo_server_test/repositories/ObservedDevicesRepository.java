package com.zelenux.upprpo_server_test.repositories;

import com.zelenux.upprpo_server_test.entities.ObservedDevices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ObservedDevicesRepository extends JpaRepository<ObservedDevices, Long>, JpaSpecificationExecutor<ObservedDevices> {

}