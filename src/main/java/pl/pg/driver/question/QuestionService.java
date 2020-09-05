package pl.pg.driver.question;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.question.dto.QuestionDto;
import pl.pg.driver.question.dto.QuestionDtoMapper;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    List<QuestionDto> findFromRange(Integer page) {
        Pageable pageRequest = PageRequest.of(page, 20);
        return questionRepository.findAll(pageRequest).stream()
                .map(QuestionDtoMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    QuestionDto findById(Long id){
        return QuestionDtoMapper.entityToDtoShow(
                questionRepository.findById(id)
                        .orElseThrow(() -> new ObjectNotFoundException(MessageContent.QUESTION_NOT_FOUND + id))
        );
    }

    List<QuestionDto> findAllByWorkoutId(Long workoutId) {
        return questionRepository.findAllByWorkoutId(workoutId).stream()
                .map(QuestionDtoMapper::entityToDtoShow)
                .collect(Collectors.toList());
    }

    Question update(QuestionDto questionDto) {
        return questionRepository.save(QuestionDtoMapper.dtoToEntity(questionDto));
    }

    Question save(QuestionDto questionDto) {
        return questionRepository.save(QuestionDtoMapper.dtoToEntity(questionDto));
    }

    void delete(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.QUESTION_NOT_FOUND + id)));
        log.info(MessageContent.QUESTION_DELETED + id);
        questionRepository.deleteById(question.getId());
    }

    public Long count() {
        return questionRepository.count();
    }
}
