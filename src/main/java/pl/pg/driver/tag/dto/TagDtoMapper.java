package pl.pg.driver.tag.dto;

import pl.pg.driver.tag.Tag;

public class TagDtoMapper {
    private TagDtoMapper() {
    }

    public static TagDto EntityToDtoShow(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .label(tag.getContents())
                .build();
    }

    public static Tag DtoToEntity(TagDto tagDto) {
        return Tag.builder()
                .id(tagDto.getId())
                .contents(tagDto.getLabel())
                .build();
    }
}
