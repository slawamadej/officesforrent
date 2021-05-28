package pl.gabinetynagodziny.officesforrent.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class CustomDaoAuthenticationProvider implements AuthenticationProvider {

    public static final String USERNAME_CANNOT_BE_NULL = "Username cannot be null";
    public static final String CREDENTIALS_CANNOT_BE_NULL = "Credentials cannot be null";
    public static final String INCORRECT_PASSWORD = "Incorrect password";

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomDaoAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    //metody ze springowego interfacu authentication provider
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("AUTHENTICATE");
        String username = authentication.getName();
        Object credentials = authentication.getCredentials();

        Assert.notNull(username, USERNAME_CANNOT_BE_NULL);
        Assert.notNull(credentials, CREDENTIALS_CANNOT_BE_NULL);

        if(!(credentials instanceof String)){
            return null;//tutaj nie optional, bo interface AuthenticationProvider zwraca nulla jak jest costam zle
        }
        String password = credentials.toString();

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        System.out.println("Takie haslo w BAZIE" + userDetails.getPassword());

        //docelowo password encoder
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException(INCORRECT_PASSWORD);
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

        return auth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);

        //dlaczego ta klasa? authenticationManager domyslnie operuje na UsernamePasswordAuthenticationToken
    }
}
