package pl.pg.driver.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.tag.dto.TagDto;
import pl.pg.driver.tag.dto.TagDtoMapper;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {
    private final TagRepository tagRepository;

    List<TagDto> findFromRange(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        return tagRepository.findAll(pageRequest).stream()
                .map(TagDtoMapper::EntityToDtoShow)
                .collect(Collectors.toList());
    }

    TagDto findById(Long id){
        return TagDtoMapper.EntityToDtoShow(
                tagRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.TAG_NOT_FOUND + id))
        );
    }

    Tag update(TagDto tagDto) {
        return tagRepository.save(TagDtoMapper.DtoToEntity(tagDto));
    }

    Tag save(TagDto tagDto) {
        return tagRepository.save(TagDtoMapper.DtoToEntity(tagDto));
    }

    void delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.TAG_NOT_FOUND + id)));
        tagRepository.deleteById(tag.getId());
    }

    public Long count() {
        return tagRepository.count();
    }
}
