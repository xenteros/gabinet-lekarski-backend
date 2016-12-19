package pl.com.gurgul;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.com.gurgul.security.PasswordEncoderImpl;

/**
 * Created by agurgul on 10.12.2016.
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/addUser").permitAll();
    }

    @Bean
    public PasswordEncoder encoder() {
        final ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        encoder.setEncodeHashAsBase64(true);
        encoder.setIterations(1024);

        return new PasswordEncoderImpl(encoder);
    }
}
