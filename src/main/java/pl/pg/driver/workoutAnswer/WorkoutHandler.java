package pl.pg.driver.workoutAnswer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.user.User;
import pl.pg.driver.user.UserRepostory;
import pl.pg.driver.workout.Workout;
import pl.pg.driver.workout.WorkoutRepository;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerHandlerDto;
import pl.pg.driver.workoutsPassed.WorkoutPassed;
import pl.pg.driver.workoutsPassed.WorkoutPassedRepository;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkoutHandler {
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final String SECRET = "mySecretKey";
    private final WorkoutAnswerRepository workoutAnswerRepository;
    private final UserRepostory userRepostory;
    private final WorkoutRepository workoutRepository;
    private final WorkoutPassedRepository workoutPassedRepository;
    private Workout workout;

    int getWorkoutsScore(HttpServletRequest request, WorkoutAnswerHandlerDto workoutAnswerHandlerDto) {
        setWorkout(workoutAnswerHandlerDto.getWorkoutId());

        long result =  workoutAnswerHandlerDto.getWorkoutAnswerList().stream()
               .filter(w -> checkAnswer(w.getQuestionId(), w.getAnswerId()))
               .count();

        boolean isExamPassed = 100 * result / workoutAnswerHandlerDto.getWorkoutAnswerList().size() > 60;

        if (isExamPassed) saveUserResult(request);

        return (isExamPassed) ? this.workout.getScore() : 0;
    }

    private boolean checkAnswer(Long questionId, Long answerId) {
        return workoutAnswerRepository.findWorkoutAnswerByQuestionIdAndAnswerId(questionId, answerId)
                .map(WorkoutAnswer::getIsCorrect)
                .orElseThrow();
    }

    private void saveUserResult(HttpServletRequest request) {
        Claims claims = getJWTClaimsFromHttpRequest(request);
        //List<String> authorities = (List) claims.get("authorities");
        User user = userRepostory.findById(Long.parseLong(claims.getSubject()))
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.USER_NOT_FOUND));
        user.setWorkoutsPoints(user.getWorkoutsPoints() + this.workout.getScore());
        saveWorkoutPassedForUserId(user);

        userRepostory.save(user);
    }

    private void saveWorkoutPassedForUserId(User user) {
        WorkoutPassed workoutPassed = WorkoutPassed.builder()
                .user(user)
                .workout(this.workout)
                .passedDate(LocalDateTime.now())
                .build();

        workoutPassedRepository.save(workoutPassed);
    }

    private void setWorkout(Long workoutId) {
        this.workout = workoutRepository.findById(workoutId)
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.WORKOUT_NOT_FOUND));
    }

    private String getTokenFromHttpRequest(HttpServletRequest request) {
        return request.getHeader(HEADER).replace(PREFIX, "");
    }
    private Claims getJWTClaimsFromHttpRequest(HttpServletRequest request) {
        String jwtToken = getTokenFromHttpRequest(request);
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(jwtToken)
                .getBody();
    }
}
//TODO dodać obsluge bledow
//TODO zmienic WorkoutAnswerRepository na service
//TODO zastanowic sie nad modyfikatorami dostepu w sewisach
//TODO zoptymalizowac pobieranie claims
