package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Role repository.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find all by status list.
     *
     * @param status the status
     * @return the list
     */
    List<Role> findAllByStatus(boolean status);

    /**
     * Find by name list.
     *
     * @param name the name
     * @return the list
     */
    List<Role> findByName(String name);

    /**
     * Find all active roles and permissions list.
     *
     * @param roleStatus       the role status
     * @param permissionStatus the permission status
     * @return the list
     */
    @Query(value = "SELECT * FROM t_role join t_role_permissions on t_role.id = t_role_permissions.role_id  join t_permission on t_permission.id=t_role_permissions.permissions_id where t_role.status= ?1 and t_permission.status= ?2", nativeQuery = true)
    List<Role> findAllActiveRolesAndPermissions(boolean roleStatus, boolean permissionStatus);

}