package com.zelenux.upprpo_server_test.observed.repositories;

import com.zelenux.upprpo_server_test.observed.entities.ObservedData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ObservedDataRepository extends JpaRepository<ObservedData, Long>, JpaSpecificationExecutor<ObservedData> {

}