package pl.pg.driver.workoutsPassed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.pg.driver.user.User;
import pl.pg.driver.workout.Workout;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "workouts_passed")
public class WorkoutPassed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passed_date" ,nullable = false)
    private LocalDateTime passedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workout workout;

    public WorkoutPassed() {
        //JPA Only
    }
}
