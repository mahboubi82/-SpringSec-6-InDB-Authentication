package ma.mahboubi.hopital.security.service;

import lombok.AllArgsConstructor;
import ma.mahboubi.hopital.security.entities.AppRole;
import ma.mahboubi.hopital.security.entities.AppUser;
import ma.mahboubi.hopital.security.repo.AppRoleRepository;
import ma.mahboubi.hopital.security.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppRoleRepository appRoleRepository;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser != null) throw new RuntimeException("user already exist");
        if (!password.equals(confirmPassword)) throw new RuntimeException("password not match");
        appUser = AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        AppUser savedAppUser = appUserRepository.save(appUser);

        return savedAppUser;
    }

    @Override
    public AppRole addRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole!=null) throw new RuntimeException("Role already exist");
        appRole= AppRole.builder()
                .role(role)
                .build();

        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppRole appRole = appRoleRepository.findById(role).get();
        AppUser appUser = appUserRepository.findByUsername(username);
        appUser.getRoles().add(appRole);

    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppRole appRole = appRoleRepository.findById(role).get();
        AppUser appUser = appUserRepository.findByUsername(username);
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loaduserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
