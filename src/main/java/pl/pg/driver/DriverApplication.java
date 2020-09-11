package pl.pg.driver;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.pg.driver.security.JWTAuthorizationFilter;
import pl.pg.driver.user.User;
import pl.pg.driver.user.UserRepostory;

@SpringBootApplication
public class DriverApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner uUser(UserRepostory userRepostory) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = User.builder()
                .role("ROLE_ADMIN")
                .firstName("Piotr")
                .lastName("Glowacki")
                .nick("pietia28")
                .email("pege28@wp.pl")
                .password(passwordEncoder().encode("test"))
                .build();

       User user1 = User.builder()
                .role("ROLE_USER")
                .firstName("Adam")
                .lastName("Nowak")
                .nick("nowako")
                .email("adam@wp.pl")
               .password(passwordEncoder().encode("test"))
                .build();
       userRepostory.save(user1);
        return args -> userRepostory.save(user);
    }

    @RequiredArgsConstructor
    @EnableWebSecurity
    @Configuration
    @EnableGlobalMethodSecurity(securedEnabled = true)
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        private final JWTAuthorizationFilter jwtAuthorizationFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/users/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/users/add").permitAll()
                    .anyRequest().authenticated();
        }
    }
}
