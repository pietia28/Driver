package pl.pg.driver.workoutAnswer;

import org.springframework.beans.factory.annotation.Autowired;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerHandlerDto;


public class WorkoutHandler {
    @Autowired
    private WorkoutAnswerRepository workoutAnswerRepository;

    boolean checkExam(WorkoutAnswerHandlerDto workoutAnswerHandlerDto) {
       long result = workoutAnswerHandlerDto.getWorkoutAnswerList().stream()
               .filter(w -> checkAnswer(w.getQuestionId(), w.getAnswerId()))
               .count();
        return 100L * result / workoutAnswerHandlerDto.getWorkoutAnswerList().size() > 60;
    }

    private boolean checkAnswer(Long questionId, Long answerId) {
        return workoutAnswerRepository.findWorkoutAnswerByQuestionIdAAndAnswerId(questionId, answerId)
                .map(WorkoutAnswer::getIsCorrect)
                .orElseThrow();
    }
}
//TODO dodać obsluge bledow
