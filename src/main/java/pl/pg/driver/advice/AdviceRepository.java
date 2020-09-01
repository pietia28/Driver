package pl.pg.driver.advice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdviceRepository extends JpaRepository<Advice, Long> {
}
