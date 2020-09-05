package pl.pg.driver.media;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.pg.driver.exception.BadFileTypeException;
import pl.pg.driver.exception.ObjectNotFoundException;
import pl.pg.driver.maessage.MessageContent;
import pl.pg.driver.question.Question;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MediaService {
    private final MediaRepository mediaRepository;

    Media findById(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.MEDIA_NOT_FOUND + id)));
    }
    
    Media save(MultipartFile file) throws IOException {
        String fileType = Objects.requireNonNull(file.getContentType()).split("/")[0];
        if (!fileType.equals("image")) throw new BadFileTypeException(MessageContent.MEDIA_BAD_FILE);

        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + "." +
                Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

        Media media = new Media();
        media.setFile(file.getBytes());
        media.setOriginName(file.getOriginalFilename());
        media.setType(file.getContentType());
        media.setUuidFileName(fileName);
        log.info(MessageContent.MEDIA_UPLOADED + fileName);
        return mediaRepository.save(media);
    }

    void delete(Long id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow((() -> new ObjectNotFoundException(MessageContent.MEDIA_NOT_FOUND + id)));
        log.info(MessageContent.MEDIA_DELETED + id);
        mediaRepository.deleteById(media.getId());
    }

    public Long count() {
        return mediaRepository.count();
    }
}
