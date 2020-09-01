package pl.pg.driver.workout;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.workout.dto.WorkoutDto;
import pl.pg.driver.workout.dto.WorkoutDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepository;

    List<WorkoutDto> findFromRange(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        return workoutRepository.findAll(pageRequest).stream()
                .map(WorkoutDtoMapper::EntityToDtoShow)
                .collect(Collectors.toList());
    }

    WorkoutDto findById(Long id){
        return WorkoutDtoMapper.EntityToDtoShow(
                workoutRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.WORKOUT_NOT_FOUND + id))
        );
    }

    Workout update(WorkoutDto workoutDto) {
        return workoutRepository.save(WorkoutDtoMapper.DtoToEntity(workoutDto));
    }

    Workout save(WorkoutDto workoutDto) {
        return workoutRepository.save(WorkoutDtoMapper.DtoToEntity(workoutDto));
    }

    void delete(Long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.WORKOUT_NOT_FOUND + id)));
        workoutRepository.deleteById(workout.getId());
    }

    public Long count() {
        return workoutRepository.count();
    }
}
