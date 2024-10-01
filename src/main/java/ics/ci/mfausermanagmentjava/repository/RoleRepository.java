package ics.ci.mfausermanagmentjava.repository;

import ics.ci.mfausermanagmentjava.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<AppRole, Long> {
   /* @Query("Select ur.appRole.roleName from " + UserRole.class.getName() + " ur " + " where ur.appUser.userId = :userId")
    List<String>getRoleNames(@Param("userId")Long userId);*/

   AppRole findByRoleId (Long id);
   AppRole findByRoleName(String roleName);

   List<AppRole> findByRoleNameIsNot(String rolename);
}
