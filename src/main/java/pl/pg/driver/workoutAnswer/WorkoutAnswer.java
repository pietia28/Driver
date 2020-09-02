package pl.pg.driver.workoutAnswer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.pg.driver.question.Question;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "workouts_answers")
public class WorkoutAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String answer;

    @Column(nullable = false)
    private Boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public WorkoutAnswer() {
        //JPA Only
    }
}
