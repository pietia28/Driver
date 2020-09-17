package pl.pg.driver.user;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.NoValidCredentialException;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.exception.UserAccessForbiddenException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.tokenBlackList.TokenBlackList;
import pl.pg.driver.tokenBlackList.TokenBlackListRepository;
import pl.pg.driver.user.dto.*;
import pl.pg.driver.user.role.UserRole;

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
    private final BCryptPasswordEncoder passwordEncoder;
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final String SECRET = "mySecretKey";

    UserAuthenticateDto login(UserLoginDto userLoginDto) {
        User user = checkCredentials(userLoginDto.getEmail(), userLoginDto.getPassword());
        user.setToken(getJWTToken(user.getId(), user.getRole()));
        UserAuthenticateDto userAuthenticateDto = UserDtoMapper.entityToDtoToken(user);
        log.info(MessageContent.USER_LOGIN_SUCCESS + userLoginDto.getEmail());
        return userAuthenticateDto;
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

    public UserDto findById(Long id, HttpServletRequest request){
        User user = userRepostory.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.USER_NOT_FOUND + id));

        Claims claims = getJWTClaimsFromHttpRequest(request);
        List<String> authorities = (List) claims.get("authorities");
        if (!claims.getSubject().equals(String.valueOf(id)) && authorities.get(0).equals(UserRole.ROLE_USER.toString())) {
                throw new UserAccessForbiddenException(MessageContent.USER_ACCESS_FORBIDDEN);
        }

        return UserDtoMapper.entityToDtoShow(user);
    }

    public User update(UserDto userDto, HttpServletRequest request) {
        Claims claims = getJWTClaimsFromHttpRequest(request);
        List<String> authorities = (List) claims.get("authorities");
        if (!claims.getSubject().equals(String.valueOf(userDto.getId())) && authorities.get(0).equals(UserRole.ROLE_USER.toString())) {
            throw new UserAccessForbiddenException(MessageContent.USER_ACCESS_FORBIDDEN);
        }

        User user = userRepostory.findById(userDto.getId())
                .orElseThrow(() -> new ObjectNotFoundException(MessageContent.USER_NOT_FOUND + userDto.getId()));
        String password = user.getPassword();
        String role = user.getRole();

        user = UserDtoMapper.dtoToEntity(userDto);
        user.setPassword(password);
        user.setRole(role);

        return userRepostory.save(user);
    }

    User save(UserCreateDto userCreateDto) {
        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        return userRepostory.save(UserDtoMapper.dtoToEntityCreate(userCreateDto));
    }

    Long count() {
        return userRepostory.count();
    }

    private User checkCredentials(String email, String password) {
        return userRepostory.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new NoValidCredentialException(MessageContent.USER_NO_VALID_CREDENTIAL + email));
    }

    private String getJWTToken(Long id, String role) {
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
                .setExpiration(new Date(System.currentTimeMillis() + 6000000))
                .signWith(SignatureAlgorithm.HS512,
                        SECRET.getBytes())
                .compact();
        return PREFIX + token;
    }

    private Claims getJWTClaimsFromHttpRequest(HttpServletRequest request) {
        String jwtToken = getTokenFromHttpRequest(request);
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private String getTokenFromHttpRequest(HttpServletRequest request) {
        return request.getHeader(HEADER).replace(PREFIX, "");
    }
}
