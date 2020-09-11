package pl.pg.driver.tokenBlackList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;

public interface TokenBlackListRepository extends JpaRepository<TokenBlackList, Long> {
    @Query("select t from TokenBlackList t where t.token =:token")
    Optional<TokenBlackList> findByToken(@Param("token") String token);
}
