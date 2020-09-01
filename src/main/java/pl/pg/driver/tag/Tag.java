package pl.pg.driver.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String contents;

    public Tag() {
        //JPA Only
    }
}
