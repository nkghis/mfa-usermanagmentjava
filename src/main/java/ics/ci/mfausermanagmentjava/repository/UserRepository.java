package ics.ci.mfausermanagmentjava.repository;

import ics.ci.mfausermanagmentjava.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByEmail(String email);

    Boolean existsByEmail(String email);

}
