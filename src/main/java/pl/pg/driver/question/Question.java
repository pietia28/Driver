package pl.pg.driver.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.pg.driver.media.Media;
import pl.pg.driver.workout.Workout;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    Workout workout;

    @ManyToOne
    @JoinColumn(name = "media_id")
    Media media;

    public Question() {
        //JPA Only
    }
}
