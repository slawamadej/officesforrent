package pl.gabinetynagodziny.officesforrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pl.gabinetynagodziny.officesforrent.provider.CustomDaoAuthenticationProvider;
import pl.gabinetynagodziny.officesforrent.service.impl.JpaUserDetailsService;
import pl.gabinetynagodziny.officesforrent.util.Constans;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    JpaUserDetailsService userDetailsService;
    private final AuthSuccessHandler authSuccessHandler;

    @Autowired
    CustomDaoAuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfig(JpaUserDetailsService userDetailsService, AuthSuccessHandler authSuccessHandler){
        this.userDetailsService = userDetailsService;
        this.authSuccessHandler = authSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeRequests()
                .antMatchers("/login").permitAll()//jaka metoda wolna od uwierzytelniania
                .antMatchers("/offices").permitAll()
                .antMatchers("/offices/search").permitAll()
                .antMatchers("/sign_up").permitAll()
                .antMatchers("/confirm_email").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/static/css/**").permitAll()
                .antMatchers("/static/img/**").permitAll()
                //.antMatchers("/offices/css/**").permitAll()
                //.antMatchers("/details/css/**").permitAll()
                .antMatchers("/units/**").authenticated()
                .antMatchers("/offices/**").authenticated()
                .antMatchers("/reservations/**").authenticated()
                .antMatchers("/notifications").authenticated()
                .antMatchers("/details").hasRole(Constans.ADMIN)
                .antMatchers("/details/**").hasRole(Constans.ADMIN)
                .antMatchers("/admin_panel").hasRole(Constans.ADMIN)
                .and()
            .formLogin()
                .loginPage("/login")
                .successHandler(this.authSuccessHandler)
                .defaultSuccessUrl("/offices", false)
                //TRUE- zawsze przekierowanie na ten url
                //a jak false na ten zasob co wczesniej klikalo
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/offices")
                //.deleteCookies("nazwa ciastka do usuniecia przy logout")
                    .and()
                        .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
