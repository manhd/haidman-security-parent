package com.haidm.web.authentication.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.temporal.Temporal;

/**
 * 根据手机号获取用户信息
 * @author shs-xxaqbzymhd
 */
@Slf4j
@Service("mobileDetailsServiceImpl")
public class MobileDetailsServiceImpl implements UserDetailsService {


    /**
     * 根据用户手机号码获取用户详细信息
     * @param mobile
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        log.info("-----------通过手机号查询用户信息 ----------------");
        //1. 通过手机号查询用户信息

        //2. 如果用户信息存在，获取权限资源

        // 封装用户信息
        return new User(mobile,"",true,
                true, true,
                true, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
