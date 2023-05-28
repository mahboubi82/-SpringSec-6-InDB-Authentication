package ma.mahboubi.hopital.security.service;

import lombok.AllArgsConstructor;
import ma.mahboubi.hopital.security.entities.AppRole;
import ma.mahboubi.hopital.security.entities.AppUser;
import org.hibernate.sql.results.graph.entity.LoadingEntityEntry;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailServiceImp implements UserDetailsService {

    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = accountService.loaduserByUsername(username);
        //String[] roles = appUser.getRoles().stream().map(rl -> rl.getRole()).toArray(String[]::new);
        List<SimpleGrantedAuthority> authorities = appUser.getRoles().stream().map(aut -> new SimpleGrantedAuthority(aut.getRole())).collect(Collectors.toList());

        UserDetails userDetails= User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(authorities)
                //.roles(roles)
                .build();

        return userDetails;
    }
}
