package com.chatapplicationspringBoot.repository;

import com.chatapplicationspringBoot.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findAllByStatus(boolean status);

    List<Role> findByName(String name);
    @Query(value = "SELECT * FROM chatapplicationrestapi.t_role inner join chatapplicationrestapi.t_role_permissions on chatapplicationrestapi.t_role.id = chatapplicationrestapi.t_role_permissions.role_id inner join chatapplicationrestapi.t_permission on chatapplicationrestapi.t_permission.id=chatapplicationrestapi.t_role_permissions.permissions_id where chatapplicationrestapi.t_role.status=true and chatapplicationrestapi.t_permission.status=true", nativeQuery = true)
    List<Role> findAllActiveRolesAndPermissions();

}