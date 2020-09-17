package pl.pg.driver.media.dto;

import pl.pg.driver.media.Media;

public class MediaDtoMapper {
    private MediaDtoMapper() {
    }

    public static MediaOnlyIdDto entityToDtoOnlyId (Media media) {
        return MediaOnlyIdDto.builder()
                .id(media.getId())
                .build();
    }
}
