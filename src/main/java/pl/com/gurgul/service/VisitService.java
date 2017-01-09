package pl.com.gurgul.service;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.gurgul.dto.VisitTO;
import pl.com.gurgul.exception.ErrorMessages;
import pl.com.gurgul.exception.ValidationError;
import pl.com.gurgul.exception.ValidationException;
import pl.com.gurgul.model.Visit;
import pl.com.gurgul.repository.UserRepository;
import pl.com.gurgul.repository.VisitRepository;
import pl.com.gurgul.utils.LoggedUserUtils;

import java.util.*;

import static pl.com.gurgul.exception.ErrorMessages.*;

/**
 * Created by agurgul on 11.12.2016.
 */
@Service
public class VisitService {

    private final Logger LOG = LoggerFactory.getLogger(VisitService.class);

    @Autowired
    VisitRepository visitRepository;

    @Autowired
    UserRepository userRepository;

    public Visit createVisit (VisitTO to) {
        LOG.info("Trying to create a new visit.");
        validate(to);
        LOG.info("Data correct");
        Visit visit = new Visit();
        visit.setDate(to.getDate());
        visit.setUser(userRepository.findByPesel(to.getUserUuid()));
        visit.setCompleted(to.getCompleted());
        visit.setNotes(to.getNotes());
        visit.setDoctor(LoggedUserUtils.getLoggedUser());
        return visitRepository.save(visit);
    }

    public Visit updateVisit(VisitTO to) {
        LOG.info("Trying to update visit.");
        if (to.getId() == null) {
            LOG.error("Visit not found.");
            throw new RuntimeException();
        }
        Visit visit = visitRepository.findOne(to.getId());
        visit.setNotes(to.getNotes());
        visit.setCompleted(to.getCompleted());
        return visitRepository.save(visit);
    }

    public Visit findVisit(String id) {
        LOG.info("Trying to find visit.");
        try {
            Visit visit = visitRepository.findOne(Long.parseLong(id));
            if (visit == null) {
                LOG.error("Visit not found.");
                throw new RuntimeException("Invalid id");
            }
            return visit;
        } catch (NumberFormatException e) {
            LOG.error("Invalid id");
            throw new RuntimeException("Id must be valid");
        }
    }

    public List<Visit> findAll() {
        return (List)visitRepository.findByDoctorUuidOrderByDateDesc(LoggedUserUtils.getLoggedUser().getUuid());
    }

    public int countTodays() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date from = cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date to = cal.getTime();

        return visitRepository.findByDoctorUuidAndDateBetween(LoggedUserUtils.getLoggedUser().getUuid(), from, to).size();
    }

    public List<Visit> findVisitsBetween(Date from, Date to) {
        if (from == null) {
            return visitRepository.findByDoctorUuidAndDateBefore(LoggedUserUtils.getLoggedUser().getUuid(), to);
        }
        return visitRepository.findByDoctorUuidAndDateBetween(LoggedUserUtils.getLoggedUser().getUuid(), from, to);
    }

    public List<Visit> findByUuid(String uuid) {
        return visitRepository.findByUserUuid(uuid);
    }

    private void validate(VisitTO to) {
        List<ValidationError> errors = new ArrayList<>();
        Visit visit = new Visit();

        if (to.getDate() == null) {
            LOG.error("Date was null.");
            errors.add(new ValidationError("date", MAY_NOT_BE_NULL));
        }
        if (to.getId() != null) {
            LOG.error("Id wasn't null.");
            errors.add(new ValidationError("id", NOT_ALLOWED));
        }
        if (to.getUserUuid() == null) {
            LOG.error("UUID was null.");
            errors.add(new ValidationError("user.uuid", MAY_NOT_BE_NULL));
        }
        if (to.getCompleted() == null ) {
            LOG.error("Completed was null.");
            errors.add(new ValidationError("completed", MAY_NOT_BE_NULL));
        }
        if (userRepository.findByPesel(to.getUserUuid()) == null) {
            LOG.error("User not found.");
            errors.add(new ValidationError("pesel", NO_SUCH_USER));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
     }
}
