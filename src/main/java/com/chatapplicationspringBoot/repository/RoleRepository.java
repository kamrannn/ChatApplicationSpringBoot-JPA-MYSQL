package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByStatus(boolean status);

/*    List<Role> findRolesByStatusAndPermissionsIsTrue(boolean status);
    List<Role> findRolesByStatus_AndPermissionsOrStatus(boolean status);*/
    List<Role> findByName(String name);
/*
    @Query(value = "SELECT * FROM t_role join t_role_permissions on t_role.id = t_role_permissions.role_id  join t_permission on t_permission.id=t_role_permissions.permissions_id where t_role.status=true and t_permission.status=true", nativeQuery = true)
    List<Role> findAllActiveRolesAndPermissions();
*/

}