package pl.pg.driver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.response.ResponseDetails;
import pl.pg.driver.user.dto.UserDto;
import pl.pg.driver.user.dto.UserDtoMapper;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping()
    ResponseEntity<ResponseDetails> findFromRange(@RequestParam Integer page, HttpServletRequest request) {
        Long totalPages = (count() / 20);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(userService.findFromRange(page))
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
                        .data(userService.findById(id))
                        .build());
    }

    @PutMapping()
    ResponseEntity<ResponseDetails> update(@Valid @RequestBody UserDto userDto) {
        User uUser = userService.update(userDto);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(UserDtoMapper.entityToDtoShow(uUser))
                        .build());
    }


    @PostMapping()
    ResponseEntity<Object> save(@Valid @RequestBody UserDto userDto) {
        User user = userService.save(userDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseDetails> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.USER_DELETED + id)
                        .build());
    }

    @GetMapping("/count")
    Long count() {
        return userService.count();
    }
}
