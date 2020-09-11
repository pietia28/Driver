package pl.pg.driver.workoutsPassed;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.workoutsPassed.dto.WorkoutPassedDto;
import pl.pg.driver.workoutsPassed.dto.WorkoutPassedDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkoutPassedService {
    private final WorkoutPassedRepository workoutPassedRepository;

    List<WorkoutPassedDto> findFromRange(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        return workoutPassedRepository.findAll(pageRequest).stream()
                .map(WorkoutPassedDtoMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    WorkoutPassedDto findById(Long id){
        return WorkoutPassedDtoMapper.entityToDtoShow(
                workoutPassedRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.WORKOUT_PASSED_NOT_FOUND + id))
        );
    }

    WorkoutPassed update(WorkoutPassedDto userDto) {
        return workoutPassedRepository.save(WorkoutPassedDtoMapper.dtoToEntity(userDto));
    }

    WorkoutPassed save(WorkoutPassedDto userDto) {
        return workoutPassedRepository.save(WorkoutPassedDtoMapper.dtoToEntity(userDto));
    }

    void delete(Long id) {
        WorkoutPassed question = workoutPassedRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.WORKOUT_PASSED_NOT_FOUND + id)));
        log.info(MessageContent.WORKOUT_PASSED_DELETED + id);
        workoutPassedRepository.deleteById(question.getId());
    }

    Long count() {
        return workoutPassedRepository.count();
    }
}
