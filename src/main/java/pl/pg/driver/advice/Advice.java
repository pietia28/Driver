package pl.pg.driver.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.pg.driver.media.Media;
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

    private Long likes = 0L;

    @Column(name = "is_tip_of_the_week")
    private Byte tipOfTheWeek = 0;

    @Column(nullable = false)
    private Long shows = 0L;

    private LocalDateTime created;

    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    @ManyToOne
    @JoinColumn(name = "media_id")
    private Media media;

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
