package pl.com.gurgul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.gurgul.dto.VisitTO;
import pl.com.gurgul.exception.ErrorMessages;
import pl.com.gurgul.exception.ValidationError;
import pl.com.gurgul.exception.ValidationException;
import pl.com.gurgul.model.Visit;
import pl.com.gurgul.repository.UserRepository;
import pl.com.gurgul.repository.VisitRepository;

import java.util.ArrayList;
import java.util.List;

import static pl.com.gurgul.exception.ErrorMessages.*;

/**
 * Created by agurgul on 11.12.2016.
 */
@Service
public class VisitService {


    @Autowired
    VisitRepository visitRepository;

    @Autowired
    UserRepository userRepository;

    public Visit createVisit (VisitTO to) {
        validate(to);
        Visit visit = new Visit();
        visit.setDate(to.getDate());
        visit.setUser(userRepository.findByUuid(to.getUserUuid()));
        visit.setCompleted(to.getCompleted());
        visit.setNotes(to.getNotes());
        return visitRepository.save(visit);
    }

    public Visit updateVisit (VisitTO to) {
        return null;
    }

    public Visit findVisit(String id) {
        try {
            Visit visit = visitRepository.findOne(Long.parseLong(id));
            if (visit == null) {
                throw new RuntimeException("Invalid id");
            }
            return visit;
        } catch (NumberFormatException e) {
            throw new RuntimeException("Id must be valid");
        }
    }

    public List<Visit> findAll() {
        return (List)visitRepository.findAll();
    }

    public List<Visit> findByUuid(String uuid) {
        return visitRepository.findByUserUuid(uuid);
    }
    private void validate(VisitTO to) {
        List<ValidationError> errors = new ArrayList<>();
        Visit visit = new Visit();

        if (to.getDate() == null) {
            errors.add(new ValidationError("date", MAY_NOT_BE_NULL));
        }
        if (to.getId() != null) {
            errors.add(new ValidationError("id", NOT_ALLOWED));
        }
        if (to.getUserUuid() == null) {
            errors.add(new ValidationError("user.uuid", MAY_NOT_BE_NULL));
        }
        if (to.getCompleted() == null ) {
            errors.add(new ValidationError("completed", MAY_NOT_BE_NULL));
        }

        if (!errors.isEmpty()){
            throw new ValidationException(errors);
        }
     }
}
