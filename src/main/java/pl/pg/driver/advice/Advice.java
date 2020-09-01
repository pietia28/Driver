package pl.pg.driver.advice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
