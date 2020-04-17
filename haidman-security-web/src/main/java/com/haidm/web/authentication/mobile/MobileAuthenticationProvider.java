package com.haidm.web.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 手机认证处理提供者
 * @author shs-xxaqbzymhd
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;


    /**
     * 认证处理
     * 1. 通过手机号码查询用户信息（UserDeatilsService 实现）
     * 2. 当查询到用户信息，则认为认证通过， 封装 Authentication 对象
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //1. 通过手机号码查询用户信息（UserDeatilsService 实现）
        MobileAuthenticationToken mobileAuthenticationToken =  (MobileAuthenticationToken)authentication;
        //获取手机号码
        String mobile = (String) mobileAuthenticationToken.getPrincipal();
        //通过手机号码查询用户信息（UserDeatilsService 实现）
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        if(null == userDetails){
            throw new AuthenticationServiceException("该手机号码未注册");
        }
        //封装到MobileAuhenticationToken 中
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(mobileAuthenticationToken.getDetails());
        return authenticationToken;
    }

    /**
     * 通过这个方法来选择对应的 provider,即选择 MobileAuthenticationProvider 处理
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return MobileAuthenticationToken.class.isAssignableFrom(aClass);
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
