package com.zelenux.upprpo_server_test.repositories;

import com.zelenux.upprpo_server_test.entities.GroupEntity;
import com.zelenux.upprpo_server_test.entities.UserGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsersGroupsRepository extends JpaRepository<UserGroupEntity, Long>, JpaSpecificationExecutor<UserGroupEntity> {
    @Query("select ug from UserGroupEntity ug where ug.user.id = ?1 and ug.group.id = ?2")
    UserGroupEntity findByUserAndGroup(Long userId, Long groupId);

    @Query("select g " +
            "from GroupEntity g " +
            "join UserGroupEntity ug on g.id = ug.group.id " +
            "where ug.user.id = ?1")
    List<GroupEntity> findByUser(Long userId);
}