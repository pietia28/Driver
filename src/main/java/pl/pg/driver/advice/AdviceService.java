package pl.pg.driver.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pg.driver.advice.dto.AdviceDto;
import pl.pg.driver.advice.dto.AdviceDtoMapper;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdviceService {
    private final AdviceRepository adviceRepository;

    List<AdviceDto> findFromRange(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        return adviceRepository.findAll(pageRequest).stream()
                .map(AdviceDtoMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    AdviceDto findById(Long id){
        return AdviceDtoMapper.entityToDtoShow(
                adviceRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.ADVICE_NOT_FOUND + id))
        );
    }

    Advice update(AdviceDto adviceDto) {
        return adviceRepository.save(AdviceDtoMapper.dtoToEntity(adviceDto));
    }

    Advice save(AdviceDto adviceDto) {
        return adviceRepository.save(AdviceDtoMapper.dtoToEntity(adviceDto));
    }

    void delete(Long id) {
        Advice question = adviceRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.ADVICE_NOT_FOUND + id)));
        log.info(MessageContent.ADVICE_DELETED + id);
        adviceRepository.deleteById(question.getId());
    }

    public Long count() {
        return adviceRepository.count();
    }
}
