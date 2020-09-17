package pl.pg.driver.advice.dto;

import lombok.Builder;
import lombok.Value;
import pl.pg.driver.media.Media;
import pl.pg.driver.media.dto.MediaOnlyIdDto;
import pl.pg.driver.tag.dto.TagDto;
import java.util.List;

@Value
@Builder
public class AdviceDtoNoLoggedUser {
    Long id;

    String title;

    String contents;

    Long number;

    Long likes;

    Long shows;

    List<TagDto> tags;

    MediaOnlyIdDto media;
}
