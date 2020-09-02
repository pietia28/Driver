package pl.pg.driver.workoutsPassed;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.response.ResponseDetails;
import pl.pg.driver.workoutsPassed.dto.WorkoutPassedDto;
import pl.pg.driver.workoutsPassed.dto.WorkoutPassedDtoMapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workout-passed")
public class WorkoutPassedController {
    private final WorkoutPassedService workoutPassedService;

    @GetMapping()
    ResponseEntity<ResponseDetails> findFromRange(@RequestParam Integer page, HttpServletRequest request) {
        Long totalPages = (count() / 20);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(workoutPassedService.findFromRange(page))
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
                        .data(workoutPassedService.findById(id))
                        .build());
    }

    @PutMapping()
    ResponseEntity<ResponseDetails> update(@Valid @RequestBody WorkoutPassedDto workoutPassedDto) {
        WorkoutPassed uWorkoutPassed = workoutPassedService.update(workoutPassedDto);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(WorkoutPassedDtoMapper.entityToDtoShow(uWorkoutPassed))
                        .build());
    }


    @PostMapping()
    ResponseEntity<Object> save(@Valid @RequestBody WorkoutPassedDto workoutPassedDto) {
        WorkoutPassed workoutPassed = workoutPassedService.save(workoutPassedDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(workoutPassed.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDetails> delete(@PathVariable Long id) {
        workoutPassedService.delete(id);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.WORKOUT_PASSED_DELETED + id)
                        .build());
    }

    @GetMapping("/count")
    Long count() {
        return workoutPassedService.count();
    }
}
