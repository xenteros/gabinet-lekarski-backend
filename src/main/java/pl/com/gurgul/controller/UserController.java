package pl.com.gurgul.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.gurgul.dto.UserTO;
import pl.com.gurgul.model.User;
import pl.com.gurgul.service.UserService;
import pl.com.gurgul.utils.LoggedUserUtils;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static pl.com.gurgul.utils.LoggedUserUtils.*;

/**
 * Created by agurgul on 10.12.2016.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    UserService userService;

    @ApiOperation(value = "addUser", nickname = "addUser")
    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_DOCTOR)")
    @RequestMapping(value = "/addUser", method = POST)
    public Long createUser(@RequestBody UserTO to) {
        LOG.info("Received request to create new user.");
        return userService.createUser(to);
    }


    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_DOCTOR)")
    @RequestMapping(value = "/get/all", method = GET)
    public List<User> findAll() {
        return userService.findAll();
    }

    @RequestMapping(value = "/me", method = GET)
    public UserTO me() {
        LOG.info("Received request for logged user details.");
        return userService.me();
    }

    @RequestMapping(value = "/update/me", method = PUT)
    public UserTO update(@RequestBody @Valid UserTO to) {
        LOG.info("Received request to update user details.");
        return userService.update(to);
    }
}
