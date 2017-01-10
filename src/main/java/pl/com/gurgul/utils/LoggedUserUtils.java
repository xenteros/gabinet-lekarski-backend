package pl.com.gurgul.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.com.gurgul.model.User;

/**
 * Created by agurgul on 29.12.2016.
 */
public class LoggedUserUtils {
    public static User getLoggedUser() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = auth.getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }

    public static boolean isAdmin(User user) {
        return user != null && user.getUserRole().equals(UserRoles.ROLE_ADMIN);
    }
    public static boolean isDoctor(User user) {
        return user != null && user.getUserRole().equals(UserRoles.ROLE_DOCTOR);
    }

}
