package ma.mahboubi.hopital;

import ma.mahboubi.hopital.entities.Patient;
import ma.mahboubi.hopital.repository.PatientRepository;
import ma.mahboubi.hopital.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class HotelApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        patientRepository.save(Patient.builder().name("NOUREDDINE").dateNaissance(new Date()).score(4800).malade(true).build());
        patientRepository.save(Patient.builder().name("ABDOU").dateNaissance(new Date()).score(1400).malade(false).build());
        patientRepository.save(Patient.builder().name("TATA").dateNaissance(new Date()).score(7700).malade(true).build());
    }

    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {

            if (jdbcUserDetailsManager.userExists("user11") != true)
                jdbcUserDetailsManager.createUser(User.withUsername("user11").password(passwordEncoder.encode("1234")).roles("USER").build());
            if (jdbcUserDetailsManager.userExists("user22") != true)
                jdbcUserDetailsManager.createUser(User.withUsername("user22").password(passwordEncoder.encode("1234")).roles("USER").build());
            if (jdbcUserDetailsManager.userExists("admin") != true)
                jdbcUserDetailsManager.createUser(User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("ADMIN", "USER").build());

        };

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //@Bean
    CommandLineRunner commandLineRunnerUserDetailService(AccountService accountService) {
        return args -> {

            accountService.addNewUser("user111", "1234", "user111@gmail.com", "1234");
            accountService.addNewUser("user222", "1234", "user222@gmail.com", "1234");
            accountService.addNewUser("admin", "1234", "admin@gmail.com", "1234");

            accountService.addRole("USER");
            accountService.addRole("ADMIN");

            accountService.addRoleToUser("admin","ADMIN" );
            accountService.addRoleToUser("admin","USER" );
            accountService.addRoleToUser("user111", "USER");
            accountService.addRoleToUser("user222", "USER");

        };

    }
}
