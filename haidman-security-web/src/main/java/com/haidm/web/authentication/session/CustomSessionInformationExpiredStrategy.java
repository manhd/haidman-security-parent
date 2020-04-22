package com.haidm.web.authentication.session;

import com.haidm.web.authentication.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * 当用户session 达到指定数量是会 执行该类
 * @author shs-xxaqbzymhd
 */
public class CustomSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {


    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent expiredEvent) throws IOException {
        //获取用户名
        UserDetails userDetails = (UserDetails)expiredEvent.getSessionInformation().getPrincipal();

        AuthenticationException exception = new AuthenticationServiceException(
                String.format("[%s]用户在另外一台电脑登录，您已被迫下线",userDetails.getUsername())
        );
        try {

            expiredEvent.getRequest().setAttribute("toAuthentication",true);
            customAuthenticationFailureHandler
                    .onAuthenticationFailure(expiredEvent.getRequest(),expiredEvent.getResponse(),exception);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
