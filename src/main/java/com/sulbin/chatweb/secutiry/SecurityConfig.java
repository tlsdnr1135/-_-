package com.sulbin.chatweb.secutiry;

import com.sulbin.chatweb.account.AccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig{

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final AccountRepository accountRepository;
    private final CustomUserDeatilsService userDeatilsService;

    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, AccountRepository accountRepository,CustomUserDeatilsService userDeatilsService) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.accountRepository = accountRepository;
        this.userDeatilsService = userDeatilsService;
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomUserDeatilsService(accountRepository);
//    }

    //웬만하면 이 방법을 써서 url permit all 하는 방식으로 할것 - 공식문서
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET,"/").permitAll()
                .antMatchers(HttpMethod.GET,"/api/account/profile").permitAll()
                .antMatchers(HttpMethod.POST,"/api/account/sign-up").permitAll()
                .antMatchers(HttpMethod.POST,"/api/login").permitAll()
                .antMatchers(HttpMethod.GET,"/api/account/home").permitAll()
                .antMatchers(HttpMethod.GET,"/api/account/hometest").permitAll()
                .anyRequest()
                .authenticated();
        httpSecurity.headers().frameOptions().sameOrigin();
        httpSecurity.csrf().disable();
        httpSecurity.authenticationProvider(customAuthenticationProvider);
        httpSecurity.userDetailsService(userDeatilsService);
        httpSecurity.httpBasic().disable();
        httpSecurity.formLogin().disable();
//        httpSecurity.formLogin()
//                .usernameParameter("name")
//                .loginProcessingUrl("/api/login")
//                .successHandler(
//                        new AuthenticationSuccessHandler() {
//                            @Override
//                            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                                System.out.println("authentication : " + authentication.getName());
//                                response.setStatus(200);
//                            }
//                        }
//                )
//                .failureHandler(
//                        new AuthenticationFailureHandler() {
//                            @Override
//                            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                                System.out.println("exception : " + exception.getMessage());
//                                System.out.println("패일 핸들러");
//                                response.sendRedirect("/login");
//                            }
//                        }
//                );
        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers("/swagger-ui","/swagger-ui/**","/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html","/swagger-ui/**");
    }
}
