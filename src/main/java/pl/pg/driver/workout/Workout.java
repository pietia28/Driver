package pl.pg.driver.workout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "workouts")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 60)
    private String title;

    @Column(nullable = false)
    private Integer score = 0;

    public Workout() {
        //JPA Only
    }
}
