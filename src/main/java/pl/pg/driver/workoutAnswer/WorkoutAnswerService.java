package pl.pg.driver.workoutAnswer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerDto;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerDtoMapper;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkoutAnswerService {
    private final WorkoutAnswerRepository workoutAnswerRepository;

    List<WorkoutAnswerDto> findFromRange(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        return workoutAnswerRepository.findAll(pageRequest).stream()
                .map(WorkoutAnswerDtoMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    WorkoutAnswerDto findById(Long id){
        return WorkoutAnswerDtoMapper.entityToDtoShow(
                workoutAnswerRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.WORKOUT_ANSWER_NOT_FOUND + id))
        );
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

    public Long count() {
        return workoutAnswerRepository.count();
    }
}
