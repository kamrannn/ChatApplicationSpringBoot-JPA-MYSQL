package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * The interface Permission repository.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    /**
     * Find all by status list.
     *
     * @param status the status
     * @return the list
     */
    List<Permission> findAllByStatus(boolean status);

    /**
     * Find the permission by name list.
     *
     * @param name the name
     * @return the list
     */
    List<Permission> findByName(String name);
}
