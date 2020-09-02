package pl.pg.driver.user.dto;

import pl.pg.driver.advice.Advice;
import pl.pg.driver.advice.dto.AdviceDto;
import pl.pg.driver.tag.dto.TagDtoMapper;
import pl.pg.driver.user.User;
import pl.pg.driver.workout.dto.WorkoutDtoMapper;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
}
