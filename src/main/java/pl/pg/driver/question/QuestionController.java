package pl.pg.driver.question;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.question.dto.QuestionDto;
import pl.pg.driver.question.dto.QuestionDtoMapper;
import pl.pg.driver.response.ResponseDetails;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping()
    ResponseEntity<ResponseDetails> findFromRange(@RequestParam Integer page, HttpServletRequest request) {
        Long totalPages = (questionService.count() / 20);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(questionService.findFromRange(page))
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
                        .data(questionService.findById(id))
                        .build());
    }

    @GetMapping("/workout/{workoutId}")
    ResponseEntity<ResponseDetails> findAllByWorkoutId(@PathVariable Long workoutId, HttpServletRequest request) {
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(questionService.findAllByWorkoutId(workoutId))
                        .build());
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update")
    ResponseEntity<ResponseDetails> update(@Valid @RequestBody QuestionDto questionDto) {
        Question uQuestion = questionService.update(questionDto);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(QuestionDtoMapper.entityToDtoShow(uQuestion))
                        .build());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    ResponseEntity<ResponseDetails> save(@Valid @RequestBody QuestionDto questionDto) {
        Question question = questionService.save(questionDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(question.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseDetails> delete(@PathVariable Long id) {
        questionService.delete(id);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.QUESTION_DELETED + id)
                        .build());
    }

    @GetMapping("/count")
    ResponseEntity<ResponseDetails> count() {
        Map<String, Long> dataResponse = new HashMap<>();
        dataResponse.put(MessageContent.ITEMS, questionService.count());
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(dataResponse)
                        .build());
    }
}
//TODO dodać obrazki do pytan i odpowiedzi
