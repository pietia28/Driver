package pl.pg.driver.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.pg.driver.tag.Tag;
import pl.pg.driver.workout.Workout;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "advices")
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false, unique = true)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column(nullable = false)
    private Long number;

    private Boolean hasLike;

    @Column(name = "is_tip_of_the_week")
    private Boolean isTipOfTheWeek;

    @Column(nullable = false)
    private Long shows;

    private LocalDateTime created;

    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @ManyToMany
    private List<Tag> tags;

    public Advice() {
        //JPA Only
    }

    @PrePersist
    public void prePersist() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updated = LocalDateTime.now();
    }
}
