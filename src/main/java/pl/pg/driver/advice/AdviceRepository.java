package pl.pg.driver.advice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdviceRepository extends JpaRepository<Advice, Long> {
    @Query("select a from Advice a where a.tipOfTheWeek = 0")
    List<Advice> findAdviceByIsNotTipOfTheWeek();

    @Query("select a from Advice a where a.tipOfTheWeek = 1")
    Optional<Advice> findAdviceByIsTipOfTheWeek();

    //@Query("FROM Students st WHERE st.id = (SELECT max(s.id) FROM Students s)")
    @Query("from Advice ad where ad.number = (select max(a.number) from Advice a)")
    Optional<Advice> findByNumberMaxValue ();
}

