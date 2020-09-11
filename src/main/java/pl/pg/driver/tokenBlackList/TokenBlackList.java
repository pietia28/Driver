package pl.pg.driver.tokenBlackList;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tokens_blacklist")
public class TokenBlackList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String token;

    public TokenBlackList() {
        //JPA Only
    }
}
