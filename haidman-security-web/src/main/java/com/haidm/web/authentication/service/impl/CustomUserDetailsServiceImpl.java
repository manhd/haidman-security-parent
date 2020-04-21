package com.haidm.web.authentication.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户认证service
 * @author shs-xxaqbzymhd
 */
@Service
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("-----------请求认证的用户名：{}---------------",userName);
        //查询请求认证的用户信息（数据库）
        if(!"root".equalsIgnoreCase(userName)){
            throw  new UsernameNotFoundException("用户名或密码错误");
        }
        // 请求认证的用户密码加密
        String passWord = passwordEncoder.encode("123123");
        return new User(userName,passWord,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
