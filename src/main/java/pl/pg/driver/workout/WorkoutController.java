package pl.pg.driver.workout;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.response.ResponseDetails;
import pl.pg.driver.workout.dto.WorkoutDto;
import pl.pg.driver.workout.dto.WorkoutDtoMapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workouts")
public class WorkoutController {
    private final WorkoutService workoutService;

    @GetMapping()
    ResponseEntity<ResponseDetails> findFromRange(@RequestParam Integer page, HttpServletRequest request) {
        Long totalPages = (count() / 20);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(workoutService.findFromRange(page))
                        .pageNumber(page)
                        .nextPage(request.getRequestURL().toString() + MessageContent.PAGE_IN_URL + ++page)
                        .totalPages(totalPages)
                        .build());
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseDetails> findById(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(workoutService.findById(id))
                        .build());
    }

    @PutMapping()
    ResponseEntity<ResponseDetails> update(@Valid @RequestBody WorkoutDto workoutDto) {
        Workout uWorkout = workoutService.update(workoutDto);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(WorkoutDtoMapper.EntityToDtoShow(uWorkout))
                        .build());
    }


    @PostMapping()
    ResponseEntity<Object> save(@Valid @RequestBody WorkoutDto workoutDto) {
        Workout workout = workoutService.save(workoutDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(workout.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDetails> delete(@PathVariable Long id) {
        workoutService.delete(id);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.WORKOUT_DELETED + id)
                        .build());
    }

    @GetMapping("/count")
    Long count() {
        return workoutService.count();
    }
}
