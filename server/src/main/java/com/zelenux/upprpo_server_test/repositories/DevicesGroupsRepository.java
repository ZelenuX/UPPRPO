package com.zelenux.upprpo_server_test.repositories;

import com.zelenux.upprpo_server_test.entities.DeviceGroupEntity;
import com.zelenux.upprpo_server_test.entities.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DevicesGroupsRepository extends JpaRepository<DeviceGroupEntity, Long>, JpaSpecificationExecutor<DeviceGroupEntity> {
    @Query("select dg from DeviceGroupEntity dg where dg.device.id = ?1 and dg.group.id = ?2")
    DeviceGroupEntity findByDeviceAndGroup(Long deviceId, Long groupId);

    @Query("select dg from DeviceGroupEntity dg where dg.group.id = ?1")
    List<DeviceGroupEntity> findAllByGroupId(Long id);
}