package com.zelenux.upprpo_server_test.repositories;

import com.zelenux.upprpo_server_test.entities.DataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataRepository extends JpaRepository<DataEntity, Long>, JpaSpecificationExecutor<DataEntity> {
    @Query("select d from DataEntity d where d.device.id = ?1")
    List<DataEntity> findAllByDeviceId(Long deviceId);

    @Query("select d from DataEntity d where d.device.id = ?1 order by d.time desc nulls last")
    List<DataEntity> findLastByDeviceId(Long deviceId);
}