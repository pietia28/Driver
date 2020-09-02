package pl.pg.driver.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pg.driver.advice.dto.AdviceDto;
import pl.pg.driver.advice.dto.AdviceDtoMapper;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.response.ResponseDetails;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/advices")
public class AdviceController {
    private final AdviceService adviceService;

    @GetMapping()
    ResponseEntity<ResponseDetails> findFromRange(@RequestParam Integer page, HttpServletRequest request) {
        Long totalPages = (count() / 20);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(adviceService.findFromRange(page))
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
                        .data(adviceService.findById(id))
                        .build());
    }

    @PutMapping()
    ResponseEntity<ResponseDetails> update(@Valid @RequestBody AdviceDto adviceDto) {
        Advice uAdvice = adviceService.update(adviceDto);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(AdviceDtoMapper.entityToDtoShow(uAdvice))
                        .build());
    }


    @PostMapping()
    ResponseEntity<Object> save(@Valid @RequestBody AdviceDto adviceDto) {
        Advice advice = adviceService.save(adviceDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(advice.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDetails> delete(@PathVariable Long id) {
        adviceService.delete(id);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.ADVICE_DELETED + id)
                        .build());
    }

    @GetMapping("/count")
    Long count() {
        return adviceService.count();
    }
}
