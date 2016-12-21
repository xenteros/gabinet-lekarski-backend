package pl.com.gurgul.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.gurgul.dto.UserTO;
import pl.com.gurgul.model.User;
import pl.com.gurgul.service.UserService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;


/**
 * Created by agurgul on 10.12.2016.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "addUser", nickname = "addUser")
    @RequestMapping(value = "/addUser", method = POST)
    public Long createUser(@RequestBody UserTO to) {
        return userService.createUser(to);
    }

    @RequestMapping(value = "/get/all", method = GET)
    public List<User> findAll() {
        return userService.findAll();
    }
}
