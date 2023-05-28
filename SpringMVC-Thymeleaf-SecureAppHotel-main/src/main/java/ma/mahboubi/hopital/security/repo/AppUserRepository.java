package ma.mahboubi.hopital.security.repo;

import ma.mahboubi.hopital.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, String> {
    AppUser findByUsername (String username);
}
