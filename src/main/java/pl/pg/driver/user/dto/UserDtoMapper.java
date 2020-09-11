package pl.pg.driver.user.dto;

import pl.pg.driver.user.User;
import pl.pg.driver.user.role.UserRole;

public class UserDtoMapper {
    private UserDtoMapper() {
    }

    public static UserDto entityToDtoShow(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .nick(user.getNick())
                .build();
    }

    public static User dtoToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .nick(userDto.getNick())
                .build();
    }

    public static User dtoToEntityLogin(UserLoginDto userLoginDto) {
        return User.builder()
                .email(userLoginDto.getEmail())
                .password(userLoginDto.getPassword())
                .build();
    }

    public static UserAuthenticateDto entityToDtoToken(User user) {
        return UserAuthenticateDto.builder()
                .token(user.getToken())
                .role(user.getRole())
                .build();
    }

    public static User dtoToEntityCreate (UserCreateDto userCreateDto) {
        return User.builder()
                .password(userCreateDto.getPassword())
                .email(userCreateDto.getEmail())
                .nick(userCreateDto.getNick())
                .lastName(userCreateDto.getLastName())
                .firstName(userCreateDto.getFirstName())
                .role(UserRole.ROLE_USER.toString())
                .build();
    }
}
