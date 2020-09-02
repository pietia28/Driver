package pl.pg.driver.workoutAnswer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.response.ResponseDetails;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerDto;
import pl.pg.driver.workoutAnswer.dto.WorkoutAnswerDtoMapper;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/workouts-answers")
public class WorkoutAnswerController {
    private final WorkoutAnswerService workoutAnswerService;

    @GetMapping()
    ResponseEntity<ResponseDetails> findFromRange(@RequestParam Integer page, HttpServletRequest request) {
        Long totalPages = (count() / 20);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(workoutAnswerService.findFromRange(page))
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
                        .data(workoutAnswerService.findById(id))
                        .build());
    }

    @PutMapping()
    ResponseEntity<ResponseDetails> update(@Valid @RequestBody WorkoutAnswerDto workoutAnswerDto) {
        WorkoutAnswer uWorkoutAnswer = workoutAnswerService.update(workoutAnswerDto);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(WorkoutAnswerDtoMapper.entityToDtoShow(uWorkoutAnswer))
                        .build());
    }


    @PostMapping()
    ResponseEntity<Object> save(@Valid @RequestBody WorkoutAnswerDto workoutAnswerDto) {
        WorkoutAnswer workoutAnswer = workoutAnswerService.save(workoutAnswerDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(workoutAnswer.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDetails> delete(@PathVariable Long id) {
        workoutAnswerService.delete(id);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.WORKOUT_ANSWER_DELETED + id)
                        .build());
    }

    @GetMapping("/count")
    Long count() {
        return workoutAnswerService.count();
    }
}
