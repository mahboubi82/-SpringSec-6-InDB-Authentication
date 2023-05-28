package ma.mahboubi.hopital.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class SecurityRestController {
    @GetMapping("/profile")
    /*public Principal authentication(Principal principal){
        return principal;
    }*/
    public Authentication authentication( Authentication authentication){
        //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }
}
