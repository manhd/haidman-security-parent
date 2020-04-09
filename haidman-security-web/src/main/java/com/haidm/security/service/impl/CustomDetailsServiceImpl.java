package com.haidm.security.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CustomDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String author) throws UsernameNotFoundException {

        if("haidman".equalsIgnoreCase(author)){
            String authorCode = passwordEncoder.encode("123");
            return new User(author,authorCode, AuthorityUtils.commaSeparatedStringToAuthorityList("haidman"));
        }else if("root".equalsIgnoreCase(author)){
            String authorCode = passwordEncoder.encode("1234");
            return new User(author,authorCode, AuthorityUtils.commaSeparatedStringToAuthorityList("haidman"));
        }else if("Guest".equalsIgnoreCase(author)){
            String authorCode = passwordEncoder.encode("12345");
            return new User(author,authorCode, AuthorityUtils.commaSeparatedStringToAuthorityList("Guest"));
        }else {
            throw new UsernameNotFoundException("用户信息不存在");
        }
    }
}
