package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findAllByStatus(boolean status);
    List<Permission> findByName(String name);
}
