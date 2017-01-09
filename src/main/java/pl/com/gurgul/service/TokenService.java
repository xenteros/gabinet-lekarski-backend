package pl.com.gurgul.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.gurgul.model.Gender;
import pl.com.gurgul.model.Token;
import pl.com.gurgul.model.User;
import pl.com.gurgul.repository.TokenRepository;
import pl.com.gurgul.repository.UserRepository;
import pl.com.gurgul.service.email.EmailService;

import static java.util.UUID.randomUUID;

/**
 * Created by agurgul on 02.01.2017.
 */
@Service
public class TokenService {


    private final Logger LOG = LoggerFactory.getLogger(TokenService.class);

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public void resetPassword(String token) {
        LOG.info("Trying to reset password basing on a token.");
        Token t = tokenRepository.findByValue(token);
        if (token == null) {
            LOG.error("Token {} doesn't exist.", token);
            throw new RuntimeException("Token doesn't exist.");
        }
        User user = userRepository.findByUuid(t.getUuid());
        String password = RandomStringUtils.randomAlphanumeric(12);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        emailService.sendPlainText(user.getEmail(), "Witamy w systemie Gabinet Lekarski", user.getFirstName() + " " + user.getLastName() + ", " +
                (user.getGender().equals(Gender.FEMALE) ? "Pani " : "Pana ") +
                "hasło to: " + password);
        tokenRepository.delete(t);
    }

    public void generateToken(String pesel) {
        LOG.info("Trying to generate a new token.");
        if (pesel == null) {
            LOG.error("Incorect PESEL: {}.", pesel);
            throw new RuntimeException("Podaj prawidłowy PESEL");
        }
        User user = userRepository.findByPesel(pesel);
        if (user == null) {
            LOG.error("Incorect PESEL: {}.", pesel);
            throw new RuntimeException("Podaj prawidłowy PESEL");
        }
        Token token = tokenRepository.findByUuid(user.getUuid());
        if (token == null) {
            token = new Token();
            token.setValue(randomUUID().toString());
            token.setUuid(user.getUuid());
        } else {
            token.setValue(randomUUID().toString());
        }
        token = tokenRepository.save(token);
        emailService.sendPlainText(user.getEmail(), "Zmiana hasła",
                "Aby zmienić hasło wejdź na adres: https://gabinetlekarski.herokuapp.com/password/change/" + token.getValue());

    }

}
