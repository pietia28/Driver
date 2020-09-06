package pl.pg.driver.user;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.NoValidCredentialException;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.user.dto.UserDto;
import pl.pg.driver.user.dto.UserDtoMapper;
import pl.pg.driver.user.dto.UserLoginDto;
import pl.pg.driver.user.dto.AuthenticatedUserDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepostory userRepostory;

    AuthenticatedUserDto login(UserLoginDto userLoginDto) {
        User user = checkCredentials(userLoginDto.getEmail(), userLoginDto.getPassword());
        user.setToken(getJWTToken(user.getEmail(), user.getRole()));
        AuthenticatedUserDto authenticatedUserDto = UserDtoMapper.entityToDtoToken(user);
        log.info(MessageContent.USER_LOGIN_SUCCES + userLoginDto.getEmail());
        return authenticatedUserDto;
    }

    private User checkCredentials(String email, String password) {
        return userRepostory.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new NoValidCredentialException(MessageContent.USER_NO_VALID_CREDENTIAL + email));
    }

    private String getJWTToken(String email, String role) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList(role);

        String token = Jwts.builder()
                .setId("driver")
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorities.stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes())
                .compact();
        return "Bearer " + token;
    }

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
