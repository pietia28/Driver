package pl.pg.driver.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.user.dto.UserDto;
import pl.pg.driver.user.dto.UserDtoMapper;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepostory userRepostory;

    List<UserDto> findFromRange(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        return userRepostory.findAll(pageRequest).stream()
                .map(UserDtoMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    UserDto findById(Long id){
        return UserDtoMapper.entityToDtoShow(
                userRepostory.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.USER_NOT_FOUND + id))
        );
    }

    User update(UserDto userDto) {
        return userRepostory.save(UserDtoMapper.dtoToEntity(userDto));
    }

    User save(UserDto userDto) {
        return userRepostory.save(UserDtoMapper.dtoToEntity(userDto));
    }

    void delete(Long id) {
        User question = userRepostory.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.USER_NOT_FOUND + id)));
        log.info(MessageContent.USER_DELETED + id);
        userRepostory.deleteById(question.getId());
    }

    public Long count() {
        return userRepostory.count();
    }
}
