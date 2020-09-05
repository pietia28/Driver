package pl.pg.driver.media;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "uuid_file_name", nullable = false, unique = true)
    private String uuidFileName;

    private String type;

    @Column(columnDefinition = "LONGBLOB")
    private byte[] file;

    public Media() {
        //JPA Only
    }
}
