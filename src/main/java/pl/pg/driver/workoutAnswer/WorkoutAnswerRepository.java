package pl.pg.driver.workoutAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WorkoutAnswerRepository extends JpaRepository<WorkoutAnswer,Long> {
    List<WorkoutAnswer> findWorkoutAnswersByQuestionId(Long id);

    Optional<WorkoutAnswer> findWorkoutAnswerByQuestionId(Long id);
}
