package pl.com.gurgul;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.com.gurgul.security.PasswordEncoderImpl;
import pl.com.gurgul.security.UserDetailsServiceImpl;

/**
 * Created by agurgul on 10.12.2016.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/index.html", "/swagger-ui.html", "/js/**", "/css/**", "/fonts/**", "/partials/**", "/api/login", "/password/**", "/icons/**").permitAll()
                .anyRequest().authenticated();
        http
                .formLogin()
                .loginPage("/api/login")
                .failureHandler((request, response, exception) -> response.sendError(HttpStatus.BAD_REQUEST.value(), "Username or password invalid"))
                .usernameParameter("user")
                .passwordParameter("password")
                .defaultSuccessUrl("/api/users/me").permitAll();
        http
                .logout()
                .invalidateHttpSession(true)
                .permitAll()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/");
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder encoder() {
        final ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
        encoder.setEncodeHashAsBase64(true);
        encoder.setIterations(1024);

        return new PasswordEncoderImpl(encoder);
    }
}
