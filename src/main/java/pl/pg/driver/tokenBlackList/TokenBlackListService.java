package pl.pg.driver.tokenBlackList;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pg.driver.exception.UserAccessForbiddenException;
import pl.pg.driver.maessage.MessageContent;

@RequiredArgsConstructor
@Service
public class TokenBlackListService {
    private final TokenBlackListRepository blackListRepository;

    public void checkIfTokenIsWithdrawn(String jwtToken) {
        if (!blackListRepository.findByToken(jwtToken).isEmpty())
            throw new UserAccessForbiddenException(MessageContent.USER_ACCESS_FORBIDDEN);
    }
}
