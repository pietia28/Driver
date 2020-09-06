package pl.pg.driver.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepostory extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
