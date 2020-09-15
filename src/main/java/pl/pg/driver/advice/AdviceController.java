package pl.pg.driver.advice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pg.driver.advice.dto.AdviceDto;
import pl.pg.driver.advice.dto.AdviceDtoCreate;
import pl.pg.driver.advice.dto.AdviceDtoMapper;
import pl.pg.driver.advice.dto.AdviceDtoUpdate;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.response.ResponseDetails;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/advices")
public class AdviceController {
    private final AdviceService adviceService;

    @GetMapping()
    ResponseEntity<ResponseDetails> findFromRange(
            @RequestParam Integer page, HttpServletRequest request) {
        Long totalPages = (adviceService.count() / 20);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(adviceService.findFromRange(page, request))
                        .pageNumber(page)
                        .nextPage(request.getRequestURL().toString() + MessageContent.PAGE_IN_URL + ++page)
                        .totalPages(totalPages)
                        .build());
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseDetails> findById(
            @PathVariable Long id, HttpServletRequest request) {
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(adviceService.findById(id, request))
                        .build());
    }

    @GetMapping("/tip")
    ResponseEntity<ResponseDetails> getTipOfTheWeek() {
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(adviceService.findIdOfTipOfTheWeekAdvice())
                        .build());
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/update")
    ResponseEntity<ResponseDetails> update(@Valid @RequestBody AdviceDtoUpdate adviceDtoUpdate) {
        Advice uAdvice = adviceService.update(adviceDtoUpdate);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(AdviceDtoMapper.entityToDtoShow(uAdvice))
                        .build());
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    ResponseEntity<ResponseDetails> save(@Valid @RequestBody AdviceDtoCreate adviceDtoCreate) {
        Advice advice = adviceService.save(adviceDtoCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(advice.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<ResponseDetails> delete(@PathVariable Long id) {
        adviceService.delete(id);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.ADVICE_DELETED + id)
                        .build());
    }

    @GetMapping("/count")
    ResponseEntity<ResponseDetails> count() {
        Map<String, Long> dataResponse = new HashMap<>();
        dataResponse.put(MessageContent.ITEMS, adviceService.count());
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(dataResponse)
                        .build());
    }

    @GetMapping("{id}/add-like")
    ResponseEntity<ResponseDetails> addLike(@PathVariable Long id) {
        adviceService.addLike(id);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                .status(MessageContent.OK)
                .message(MessageContent.LIKE_ADD + id)
                .build());
    }

    @GetMapping("{id}/remove-like")
    ResponseEntity<ResponseDetails> removeLike(@PathVariable Long id) {
        adviceService.removeLike(id);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.LIKE_REMOVE + id)
                        .build());
    }
}
