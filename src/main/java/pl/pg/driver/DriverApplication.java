package pl.pg.driver;

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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pl.pg.driver.security.JWTAuthorizationFilter;
import pl.pg.driver.user.User;
import pl.pg.driver.user.UserRepostory;

@SpringBootApplication
public class DriverApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverApplication.class, args);
    }

    @Bean
    CommandLineRunner uUser(UserRepostory userRepostory) {
        User user = User.builder()
                .role("ROLE_ADMIN")
                .firstName("Piotr")
                .lastName("Glowacki")
                .nick("pietia28")
                .email("pege28@wp.pl")
                .password("test")
                .build();

       User user1 = User.builder()
                .role("ROLE_USER")
                .firstName("Adam")
                .lastName("Nowak")
                .nick("nowako")
                .email("adam@wp.pl")
                .password("test1")
                .build();
       userRepostory.save(user1);
        return args -> userRepostory.save(user);
    }

    @EnableWebSecurity
    @Configuration
    @EnableGlobalMethodSecurity(securedEnabled = true)
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/users/login").permitAll()
                    //.antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                    .anyRequest().authenticated();
        }
    }
}
