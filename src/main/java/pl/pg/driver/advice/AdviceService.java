package pl.pg.driver.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.pg.driver.advice.dto.AdviceDtoCreate;
import pl.pg.driver.advice.dto.AdviceDtoMapper;
import pl.pg.driver.advice.dto.AdviceDtoUpdate;
import pl.pg.driver.exception.NoTipOfTheWeekendException;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdviceService {
    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";
    private final AdviceRepository adviceRepository;

    List<Object> findFromRange(Integer page, HttpServletRequest request) {
        Pageable pageRequest = PageRequest.of(page, 20);
        if (checkJWTToken(request)) {
            return adviceRepository.findAll(pageRequest).stream()
                    .map(AdviceDtoMapper::entityToDtoShow)
                    .collect(Collectors.toList());
        }

        return adviceRepository.findAll(pageRequest).stream()
                .map(AdviceDtoMapper::entityToDtoNoLoggedUserShow)
                .collect(Collectors.toList());
    }

    Object findById(Long id, HttpServletRequest request){
        increaseShowsNumber(id);

        if (checkJWTToken(request)) {
            return AdviceDtoMapper.entityToDtoShow(
                    adviceRepository.findById(id).get()
            );
        }

        return AdviceDtoMapper.entityToDtoNoLoggedUserShow(
                adviceRepository.findById(id).get()
        );
    }

    Long findIdOfTipOfTheWeekAdvice() {
            return adviceRepository.findAdviceByIsTipOfTheWeek()
                    .map(Advice::getId)
                    .orElseThrow(() -> new NoTipOfTheWeekendException(MessageContent.TIP_NOT_FOUND));
    }

    Advice update(AdviceDtoUpdate adviceDtoUpdate) {
        Advice advice = adviceRepository.findById(adviceDtoUpdate.getId())
                .orElseThrow(() -> new ObjectNotFoundException(
                        MessageContent.ADVICE_NOT_FOUND + adviceDtoUpdate.getId()));

        return adviceRepository.save(getUpdatedAdvice(adviceDtoUpdate, advice));
    }

    Advice save(AdviceDtoCreate adviceDtoCreate) {
        Long number = adviceRepository.findByNumberMaxValue()
                .map(Advice::getNumber)
                .orElse(1L);

        Advice advice = AdviceDtoMapper.dtoToEntityCreate(adviceDtoCreate);
        advice.setNumber(++number);
        advice.setShows(0L);
        advice.setTipOfTheWeek((byte) 0);
        advice.setLikes(0L);

        return adviceRepository.save(advice);
    }

    void delete(Long id) {
        Advice advice = adviceRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.ADVICE_NOT_FOUND + id)));
        log.info(MessageContent.ADVICE_DELETED + id);
        adviceRepository.deleteById(advice.getId());
    }

    Long count() {
        return adviceRepository.count();
    }

    void addLike(Long id) {
        Advice advice = adviceRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.ADVICE_NOT_FOUND + id)));
        advice.setLikes(advice.getLikes() + 1);
        adviceRepository.save(advice);
    }

    void removeLike(Long id) {
        Advice advice = adviceRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.ADVICE_NOT_FOUND + id)));
        long likeNumbers = advice.getLikes() - 1;
        if (likeNumbers < 0L)
            likeNumbers = 0L;
        advice.setLikes(likeNumbers);
        adviceRepository.save(advice);
    }

    @Scheduled(cron = "*/10 * * * * *")
    private void adviceOfTheWeek() {
        Optional<Advice> advice = adviceRepository.findAdviceByIsTipOfTheWeek();
        advice.ifPresent(a -> {
            a.setTipOfTheWeek((byte) 2);
            adviceRepository.save(a);
        });

        List<Advice> adviceList = adviceRepository.findAdviceByIsNotTipOfTheWeek();
        if (!adviceList.isEmpty()) {
            Random random = new Random();
            Advice advice1 = adviceRepository.findById(adviceList.get(random.nextInt(adviceList.size())).getId())
                    .orElse(null);
            advice1.setTipOfTheWeek((byte) 1);
            adviceRepository.save(advice1);
        } else {
            List<Advice> adviceList1 = adviceRepository.findAll();
            adviceList1.forEach(
                    a -> {
                        a.setTipOfTheWeek((byte) 0);
                        adviceRepository.save(a);
                    }
            );
        }
    }

    private boolean checkJWTToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

    private void increaseShowsNumber(Long id) {
        Advice advice = adviceRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.ADVICE_NOT_FOUND + id)));
        advice.setShows(advice.getShows() + 1);
        adviceRepository.save(advice);
    }

    private Advice getUpdatedAdvice(AdviceDtoUpdate adviceDtoUpdate, Advice advice) {
        Advice adviceUpdate = AdviceDtoMapper.dtoToEntityUpdate(adviceDtoUpdate);
        adviceUpdate.setShows(advice.getShows());
        adviceUpdate.setLikes(advice.getLikes());
        adviceUpdate.setNumber(advice.getNumber());
        return adviceUpdate;
    }
}
