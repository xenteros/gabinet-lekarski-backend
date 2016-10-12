package pl.com.gurgul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gurgul.model.Survey;

/**
 * Created by agurgul on 12.10.2016.
 */
@Transactional
public interface SurveyRepository extends JpaRepository<Survey, Long> {

    public Survey findById(Long id);
}
