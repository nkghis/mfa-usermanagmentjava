package ics.ci.mfausermanagmentjava.repository;

import ics.ci.mfausermanagmentjava.entity.AppRole;
import ics.ci.mfausermanagmentjava.entity.AppUser;
import ics.ci.mfausermanagmentjava.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByAppUser(AppUser user);
    List<UserRole> findByAppRole(AppRole role);
}
