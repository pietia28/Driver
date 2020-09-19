package pl.pg.driver.workoutAnswer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerHandlerDto;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerShowDto;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerDtoMapper;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkoutAnswerService {
    private final WorkoutAnswerRepository workoutAnswerRepository;
    private final WorkoutHandler workoutHandler;

    List<WorkoutAnswerShowDto> findFromRange(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        return workoutAnswerRepository.findAll(pageRequest).stream()
                .map(WorkoutAnswerDtoMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    WorkoutAnswerShowDto findById(Long id){
        return WorkoutAnswerDtoMapper.entityToDtoShow(
                workoutAnswerRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.WORKOUT_ANSWER_NOT_FOUND + id))
        );
    }

    List<WorkoutAnswerShowDto> findAllByQuestionId(Long id) {
        return workoutAnswerRepository.findWorkoutAnswersByQuestionId(id).stream()
                .map(WorkoutAnswerDtoMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    WorkoutAnswer update(WorkoutAnswerDto workoutAnswerDto) {
        return workoutAnswerRepository.save(WorkoutAnswerDtoMapper.dtoToEntity(workoutAnswerDto));
    }

    WorkoutAnswer save(WorkoutAnswerDto workoutAnswerDto) {
        return workoutAnswerRepository.save(WorkoutAnswerDtoMapper.dtoToEntity(workoutAnswerDto));
    }

    void delete(Long id) {
        WorkoutAnswer workout = workoutAnswerRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.WORKOUT_ANSWER_NOT_FOUND + id)));
        log.info(MessageContent.WORKOUT_ANSWER_DELETED + id);
        workoutAnswerRepository.deleteById(workout.getId());
    }

    Long count() {
        return workoutAnswerRepository.count();
    }

    int answers(HttpServletRequest request, WorkoutAnswerHandlerDto workoutAnswerHandlerDto) {
        return workoutHandler.getWorkoutsScore(request, workoutAnswerHandlerDto);
    }
}
