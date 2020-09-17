package pl.pg.driver.workoutAnswer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkoutAnswerRepository extends JpaRepository<WorkoutAnswer,Long> {
    List<WorkoutAnswer> findWorkoutAnswersByQuestionId(Long id);

    @Query("select w from WorkoutAnswer w where w.question.id =:questionId and w.id =:answerId")
    Optional<WorkoutAnswer> findWorkoutAnswerByQuestionIdAndAnswerId(Long questionId, Long answerId);
}
