package pl.com.gurgul.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.com.gurgul.repository.UserRepository;

/**
 * Created by agurgul on 28.12.2016.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        LOG.info("User with PESEL {} trying to log in.", s);
        UserDetails ud = userRepository.findByPesel(s);
        return ud;
    }
}
