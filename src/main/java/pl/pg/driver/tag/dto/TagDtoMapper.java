package pl.pg.driver.tag.dto;

import pl.pg.driver.tag.Tag;

public class TagDtoMapper {
    private TagDtoMapper() {
    }

    public static TagDto entityToDtoShow(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .contents(tag.getContents())
                .build();
    }

    public static Tag dtoToEntity(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .contents(tagDto.getContents())
                .build();
    }
}
