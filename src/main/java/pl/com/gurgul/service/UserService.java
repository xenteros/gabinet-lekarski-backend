package pl.com.gurgul.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.gurgul.dto.UserTO;
import pl.com.gurgul.model.Gender;
import pl.com.gurgul.model.User;
import pl.com.gurgul.repository.UserRepository;
import pl.com.gurgul.security.PasswordEncoderImpl;
import pl.com.gurgul.utils.UserRoles;

import java.util.Date;
import java.util.UUID;

import static java.util.UUID.randomUUID;

/**
 * Created by agurgul on 10.12.2016.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Long createUser(UserTO to) {
        User newUser = new User();

        newUser.setPassword(passwordEncoder.encode(to.getPassword()));
        newUser.setFirstName(to.getFirstName());
        newUser.setLastName(to.getLastName());
        newUser.setCreatedAt(new Date());
        newUser.setEmail(to.getEmail());
        newUser.setGender(Gender.valueOf(to.getGender()));
        newUser.setPhoneNumber(to.getPhoneNumber());
        newUser.setUpdatedAt(null);
        newUser.setUserRole(UserRoles.ROLE_USER);
        newUser.setUuid(randomUUID().toString());

        return userRepository.save(newUser).getId();
    }

    private void validate() {

    }
}
