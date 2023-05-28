package ma.mahboubi.hopital.security.repo;

import ma.mahboubi.hopital.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole, String> {
}
