package com.sulbin.chatweb.account;

import com.sulbin.chatweb.account.dto.AccountDto;
import com.sulbin.chatweb.account.entity.Account;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public AccountService(PasswordEncoder passwordEncoder, AccountRepository accountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.accountRepository = accountRepository;
    }

    public void signUp(AccountDto accountDto){

        Account account = Account.builder()
                .name(accountDto.getName())
                .password(accountDto.getPassword())
                .role(Role.ROLE_MEMBER)
                .build();
        account.EncodePassword(passwordEncoder);

        accountRepository.save(account);

    }
    //로그인도 해야함.

}
