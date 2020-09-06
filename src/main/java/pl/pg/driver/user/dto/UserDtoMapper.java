package pl.pg.driver.user.dto;

import pl.pg.driver.user.User;

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
                .workoutsPoints(user.getWorkoutsPoints())
                .build();
    }

    public static User dtoToEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .nick(userDto.getNick())
                .workoutsPoints(userDto.getWorkoutsPoints())
                .build();
    }

    public static User dtoToEntityLogin(UserLoginDto userLoginDto) {
        return User.builder()
                .email(userLoginDto.getEmail())
                .password(userLoginDto.getPassword())
                .build();
    }

    public static AuthenticatedUserDto entityToDtoToken(User user) {
        return AuthenticatedUserDto.builder()
                .token(user.getToken())
                .role(user.getRole())
                .build();
    }
}
