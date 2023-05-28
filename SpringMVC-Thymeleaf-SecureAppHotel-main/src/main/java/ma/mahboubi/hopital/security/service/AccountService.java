package ma.mahboubi.hopital.security.service;

import ma.mahboubi.hopital.security.entities.AppRole;
import ma.mahboubi.hopital.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addRole(String role);
    void addRoleToUser (String username, String role);
    void removeRoleFromUser(String username, String role);
    AppUser loaduserByUsername(String username);
}
