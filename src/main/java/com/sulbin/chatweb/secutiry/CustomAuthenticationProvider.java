package com.sulbin.chatweb.secutiry;

import com.sulbin.chatweb.account.entity.Account;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDeatilsService userDeatilsService;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, CustomUserDeatilsService userDeatilsService) {
        this.passwordEncoder = passwordEncoder;
        this.userDeatilsService = userDeatilsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = String.valueOf(authentication.getCredentials());

        UserDetails userDetails = userDeatilsService.loadUserByUsername(name);

        System.out.println("내가 입력한 아이디" + name );
        System.out.println("아이디" + userDetails.getUsername() );
        System.out.println("내가 입력한 패스워드" + password );
        System.out.println("패스워드" + userDetails.getPassword() );

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
