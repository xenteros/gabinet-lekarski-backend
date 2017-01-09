package pl.com.gurgul.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.com.gurgul.dto.PasswordTO;
import pl.com.gurgul.service.TokenService;
import pl.com.gurgul.service.UserService;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by agurgul on 02.01.2017.
 */
@Controller
public class PasswordController {

    private final Logger LOG = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/password/change/{token}", method = GET)
    public String  newPassword(@PathVariable String token) {
        LOG.info("Received request for a new password");
        tokenService.resetPassword(token);
        LOG.info("Password change successful");
        return "redirect:/#";
    }

    @RequestMapping(value = "/password/forgotten/{pesel}", method = GET)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void forgotten(@PathVariable String pesel) {
        LOG.info("Received request for an email. Sending");
        tokenService.generateToken(pesel);
        LOG.info("Sending email successful");
    }

    @RequestMapping(value = "/changePassword", method = PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void changePassword(@RequestBody PasswordTO passwordTO) {
        LOG.info("Received request for a password change.");
        userService.changePassword(passwordTO);
        LOG.info("Password changed.");
    }
}
