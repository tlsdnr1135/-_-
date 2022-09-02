package com.sulbin.chatweb.secutiry;

import com.sulbin.chatweb.account.AccountRepository;
import com.sulbin.chatweb.account.entity.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDeatilsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    public CustomUserDeatilsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("신욱");
        Account account = accountRepository.findByName(username).orElseThrow(
                ()-> new IllegalArgumentException("해당하는 아이디가 없습니다. (로그인)(loadUserByUsername)")
        );
        CustomUserDetails customUserDetails = CustomUserDetails.builder()
                .name(account.getName())
                .password(account.getPassword())
                .role(account.getRole())
                .build();

        return customUserDetails;
    }
}
