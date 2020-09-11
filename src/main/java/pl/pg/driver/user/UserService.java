package pl.pg.driver.user;

import io.jsonwebtoken.Claims;
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
import pl.pg.driver.exception.UserAccessForbiddenException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.tokenBlackList.TokenBlackList;
import pl.pg.driver.tokenBlackList.TokenBlackListRepository;
import pl.pg.driver.user.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepostory userRepostory;
    private final TokenBlackListRepository tokenBlackListRepository;

    UserAuthenticateDto login(UserLoginDto userLoginDto) {
        User user = checkCredentials(userLoginDto.getEmail(), userLoginDto.getPassword());
        user.setToken(getJWTToken(user.getId(), user.getRole()));
        UserAuthenticateDto userAuthenticateDto = UserDtoMapper.entityToDtoToken(user);
        log.info(MessageContent.USER_LOGIN_SUCCESS + userLoginDto.getEmail());
        return userAuthenticateDto;
    }

    private User checkCredentials(String email, String password) {
        return userRepostory.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new NoValidCredentialException(MessageContent.USER_NO_VALID_CREDENTIAL + email));
    }

    private String getJWTToken(Long id, String role) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList(role);

        String token = Jwts.builder()
                .setId("driver")
                .setSubject(String.valueOf(id))
                .claim("userId", String.valueOf(id))
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

    void logout(HttpServletRequest request) {
        String jwtToken = getTokenFromHttpRequest(request);

        TokenBlackList tokenBlackList = new TokenBlackList();
        tokenBlackList.setToken(jwtToken.trim());
        tokenBlackListRepository.save(tokenBlackList);
    }

    List<UserDto> findFromRange(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        return userRepostory.findAll(pageRequest).stream()
                .map(UserDtoMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    UserDto findById(Long id, HttpServletRequest request){
        User user = userRepostory.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.USER_NOT_FOUND + id));

        String jwtToken = getTokenFromHttpRequest(request);
        Claims claims = Jwts.parser()
                .setSigningKey("mySecretKey".getBytes())
                .parseClaimsJws(jwtToken)
                .getBody();

        List<String> authorities = (List) claims.get("authorities");

        if (!claims.getSubject().equals(String.valueOf(id)) && authorities.get(0).equals("ROLE_USER")) {
                throw new UserAccessForbiddenException(MessageContent.USER_ACCESS_FORBIDDEN);
        }

        return UserDtoMapper.entityToDtoShow(user);
    }

    private String getTokenFromHttpRequest(HttpServletRequest request) {
        return request.getHeader("Authorization").replace("Bearer", "");
    }

    User update(UserDto userDto) {
        return userRepostory.save(UserDtoMapper.dtoToEntity(userDto));
    }

    User save(UserCreateDto userCreateDto) {
        return userRepostory.save(UserDtoMapper.dtoToEntityCreate(userCreateDto));
    }

    void delete(Long id) {
        User question = userRepostory.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.USER_NOT_FOUND + id)));
        log.info(MessageContent.USER_DELETED + id);
        userRepostory.deleteById(question.getId());
    }

    Long count() {
        return userRepostory.count();
    }
}
