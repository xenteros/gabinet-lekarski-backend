package pl.com.gurgul.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.hibernate.validator.constraints.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.gurgul.dto.PasswordTO;
import pl.com.gurgul.dto.UserTO;
import pl.com.gurgul.model.Gender;
import pl.com.gurgul.model.User;
import pl.com.gurgul.repository.UserRepository;
import pl.com.gurgul.service.email.EmailService;
import pl.com.gurgul.utils.LoggedUserUtils;
import pl.com.gurgul.utils.UserRoles;

import java.util.Date;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Created by agurgul on 10.12.2016.
 */
@Service
public class UserService {

    private final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailService emailService;

    public Long createUser(UserTO to) {
        LOG.info("Trying to create new user.");
        User newUser = new User();

        validate(to);
        newUser.setFirstName(to.getFirstName());
        newUser.setLastName(to.getLastName());
        newUser.setCreatedAt(new Date());
        newUser.setEmail(to.getEmail());
        newUser.setGender(Gender.valueOf(to.getGender()));
        newUser.setPhoneNumber(to.getPhoneNumber());
        newUser.setUpdatedAt(null);
        newUser.setUserRole(UserRoles.ROLE_USER);
        newUser.setUuid(randomUUID().toString());
        newUser.setPesel(to.getPesel());

        String password = RandomStringUtils.randomAlphanumeric(12);
        newUser.setPassword(passwordEncoder.encode(password));
        User user = userRepository.save(newUser);
        emailService.sendPlainText(user.getEmail(), "Witamy w systemie Gabinet Lekarski", user.getFirstName() + " " + user.getLastName() + ", " +
                        (user.getGender().equals(Gender.FEMALE) ? "Pani " : "Pana ") +
                "konto zostało utworzone. \n Aktualne hasło to: " + password);
        return user.getId();
    }

    public List<User> findAll() {
        LOG.info("Trying to find all users.");
        return userRepository.findAll();
    }

    public UserTO update(UserTO to) {
        LOG.info("Trying to update user.");
        User loggedUser = LoggedUserUtils.getLoggedUser();
        if (!to.getEmail().equals(loggedUser.getEmail()) && EmailValidator.getInstance().isValid(to.getEmail())) {
            loggedUser.setEmail(to.getEmail());
        }
        if (!to.getPhoneNumber().equals(loggedUser.getPhoneNumber()) && StringUtils.isNumeric(to.getPhoneNumber())) {
            loggedUser.setPhoneNumber(to.getPhoneNumber());
        }
        return new UserTO(userRepository.save(loggedUser));

    }

    public void changePassword(PasswordTO to) {
        LOG.info("Trying to user's password.");
        User user = userRepository.findByPesel(to.getPesel());
        if (user == null) {
            LOG.error("Incorrect PESEL: {}", to.getPesel());
            throw new RuntimeException("Nieprawidłowy PESEL");
        }
        if (!user.getPassword().equals(passwordEncoder.encode(to.getOldPassword()))) {
            LOG.error("Incorrect password");
            throw new RuntimeException("Podano złe stare hasło.");
        }
        if (StringUtils.isBlank(to.getNewPassword())) {
            LOG.error("Empty password");
            throw new RuntimeException("Hasło nie może być puste.");
        }
        if (to.getNewPassword().length() > 63) {
            LOG.error("Too long password: {}", to.getNewPassword());
            throw new RuntimeException("Hasło nie może być dłuższe niż 63 znaki.");
        }
        user.setPassword(passwordEncoder.encode(to.getNewPassword()));
        userRepository.save(user);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    public UserTO me() {
        User me = LoggedUserUtils.getLoggedUser();
        return new UserTO(me);
    }
    private void validate(UserTO to) {
        if (isEmpty(to.getPesel())) {
            throw new RuntimeException();
        }
        User user = userRepository.findByPesel(to.getPesel());
        if (user != null) {
            LOG.error("User with PESEL {} already has an account.", to.getPesel());
            throw new RuntimeException("Użytkownik z takim numerem PESEL już istnieje.");
        }
        if (isEmpty(to.getEmail()) || !EmailValidator.getInstance().isValid(to.getEmail())) {
            LOG.error("Incorrect email {}.", to.getEmail());
            throw new RuntimeException("Nieprawidłowy adres email.");
        }
        if (isEmpty(to.getFirstName()) || isEmpty(to.getLastName()) || isEmpty(to.getPhoneNumber())) {
            LOG.error("One of the properties was empty.");
            throw new RuntimeException();
        }
        if (Gender.valueOf(to.getGender()) == null) {
            LOG.error("Incorrect gender: {}.", to.getGender());
            throw new RuntimeException();
        }

    }
}
