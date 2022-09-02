package com.sulbin.chatweb.account.entity;

import com.sulbin.chatweb.account.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Account(Long id, String name, String password, Role role){
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public void EncodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

}
