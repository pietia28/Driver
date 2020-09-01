package pl.pg.driver.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepostory extends JpaRepository<User, Long> {
}
