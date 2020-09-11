package pl.pg.driver.user;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.response.ResponseDetails;
import pl.pg.driver.tokenBlackList.TokenBlackListRepository;
import pl.pg.driver.user.dto.UserCreateDto;
import pl.pg.driver.user.dto.UserDto;
import pl.pg.driver.user.dto.UserDtoMapper;
import pl.pg.driver.user.dto.UserLoginDto;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final TokenBlackListRepository tokenBlackListRepository;

    @PostMapping("/login")
    ResponseEntity<ResponseDetails> login(@RequestBody UserLoginDto userLoginDto) {
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.USER_LOGIN_SUCCESS + userLoginDto.getEmail())
                        .data(userService.login(userLoginDto))
                        .build());
    }

    @PostMapping("/logout")
    ResponseEntity<ResponseDetails> logout(HttpServletRequest request) {
        userService.logout(request);

        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .message(MessageContent.USER_LOGOUT)
                        .build());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping()
    ResponseEntity<ResponseDetails> findFromRange(@RequestParam Integer page, HttpServletRequest request) {
        Long totalPages = (userService.count() / 20);
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
    ResponseEntity<ResponseDetails> findById(HttpServletRequest request, @PathVariable Long id) {
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(userService.findById(id, request))
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
    ResponseEntity<ResponseDetails> save(@Valid @RequestBody UserCreateDto userCreateDto) {
        User user = userService.save(userCreateDto);
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
    ResponseEntity<ResponseDetails> count() {
        Map<String, Long> dataResponse = new HashMap<>();
        dataResponse.put(MessageContent.ITEMS, userService.count());
        return ResponseEntity.ok()
                .body(ResponseDetails.builder()
                        .status(MessageContent.OK)
                        .data(dataResponse)
                        .build());
    }
}
