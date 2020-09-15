package pl.pg.driver;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.pg.driver.advice.Advice;
import pl.pg.driver.advice.AdviceRepository;
import pl.pg.driver.security.JWTAuthorizationFilter;
import pl.pg.driver.tag.Tag;
import pl.pg.driver.tag.TagRepository;
import pl.pg.driver.user.User;
import pl.pg.driver.user.UserRepostory;
import pl.pg.driver.workoutAnswer.WorkoutHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@EnableScheduling
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
    WorkoutHandler workoutHandler() {
        return new WorkoutHandler();
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

    @Bean
    CommandLineRunner uTag(TagRepository repository) {
        Faker faker = new Faker();
        return args -> IntStream.range(0, 5).forEach(
                i -> repository.save(Tag.builder()
                        .contents(faker.lorem().word())
                        .build())
        );
    }

    @Bean
    CommandLineRunner uAdvice(AdviceRepository repository, TagRepository tagRepository) {
        List<Tag> tags = new ArrayList<>();
        tags.add(tagRepository.findById(1L)
            .orElse(null));
        Long n = 1L;
        Faker faker = new Faker();
        return args -> LongStream.range(0, 5).forEach(
                i -> repository.save(Advice.builder()
                        .contents(faker.lorem().sentence())
                        .likes(0L)
                        .shows(0L)
                        .number(i + 1)
                        .title(faker.lorem().word())
                        .tags(tags)
                        .tipOfTheWeek((byte) 0)
                        .build()
        ));
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
                    .antMatchers(HttpMethod.GET, "/advices").permitAll()
                    .antMatchers(HttpMethod.GET, "/advices/*").permitAll()
                    .antMatchers(HttpMethod.GET, "/advices/*/*").permitAll()
                    .antMatchers(HttpMethod.GET, "/advices/tip").permitAll()
                    .anyRequest().authenticated();
        }
    }
}
