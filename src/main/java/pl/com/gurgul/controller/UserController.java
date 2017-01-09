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

/**
 * @RestController - anotacja wskazuje na to, że jest to komponent, który należy
 *                   zainicjalizować przy uruchamianiu aplikacji. Ponadto, zwracane
 *                   przez metody obiekty, będą umieszczane w HttpResponse#Body
 *
 * @RequestMapping - anotacja, która rozszerza wszystkie mappingi metod o prefix
 *                   /api/users
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger LOG = LoggerFactory.getLogger(PasswordController.class);

    @Autowired
    UserService userService;

    /**
     *  @PreAuthorize   - anotacja, która wymusza na zalogowanym użytkowniku
     *                    posiadanie konkretnej roli w systemie. W tym przypadku ROLE_DOCTOR
     *  @RequestMapping - anotacja, która zawęża ostatecznie klasę zapytań, które
     *                    zostaną przekierowane do tej metody. Będą to zapytania pod
     *                    adresem /api/users/addUser, z metodą POST
     *  @RequestBody    - Body zapytania zostanie przez Spring zmapowane do obiektu klasy
     *                    UserTO.
     *  Metoda zwraca id utworzonego pacjenta.
     */
    @ApiOperation(value = "addUser", nickname = "addUser")
//    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_DOCTOR)")
    @RequestMapping(value = "/addUser", method = POST)
    public Long createUser(@RequestBody UserTO to) {
        LOG.info("Received request to create new user.");
        return userService.createUser(to);
    }


    /**
     *  @PreAuthorize   - anotacja, która wymusza na zalogowanym użytkowniku
     *                    posiadanie konkretnej roli w systemie. W tym przypadku ROLE_DOCTOR
     *  @RequestMapping - anotacja, która zawęża ostatecznie klasę zapytań, które
     *                    zostaną przekierowane do tej metody. Będą to zapytania pod
     *                    adresem /api/users/get/all, z metodą GET
     *  Metoda zwraca listę wszystkich użytkowników.
     */
    @PreAuthorize("hasAuthority(T(pl.com.gurgul.utils.UserRoles).ROLE_DOCTOR)")
    @RequestMapping(value = "/get/all", method = GET)
    public List<User> findAll() {
        return userService.findAll();
    }

    /**
     *  @RequestMapping - anotacja, która zawęża ostatecznie klasę zapytań, które
     *                    zostaną przekierowane do tej metody. Będą to zapytania pod
     *                    adresem /api/users/me, z metodą GET
     *
     *  Metoda zwraca informacje o aktualnie zalogowanym użytkowniku.
     */
    @RequestMapping(value = "/me", method = GET)
    public UserTO me() {
        LOG.info("Received request for logged user details.");
        return userService.me();
    }

    /**
     *  @RequestMapping - anotacja, która zawęża ostatecznie klasę zapytań, które
     *                    zostaną przekierowane do tej metody. Będą to zapytania pod
     *                    adresem /api/users/update/me, z metodą PUT
     *
     *  Metoda zwraca zaktualizowane informacje o aktualnie zalogowanym użytkowniku.
     */
    @RequestMapping(value = "/update/me", method = PUT)
    public UserTO update(@RequestBody @Valid UserTO to) {
        LOG.info("Received request to update user details.");
        return userService.update(to);
    }
}
