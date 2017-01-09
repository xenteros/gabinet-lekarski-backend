package pl.com.gurgul.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.com.gurgul.model.Visit;

import java.util.Date;
import java.util.List;

/**
 * Created by agurgul on 11.12.2016.
 */
@Repository
public interface VisitRepository extends CrudRepository<Visit, Long>{
    List<Visit> findByUserUuid(String uuid);
    List<Visit> findByDateBetween(Date from, Date to);
    List<Visit> findByDoctorUuidAndDateBetween(String uuid, Date from, Date to);
    List<Visit> findByDoctorUuidAndDateBefore(String uuid, Date to);
    List<Visit> findAllByOrderByDateDesc();
    List<Visit> findByDoctorUuidOrderByDateDesc(String uuid);
}
