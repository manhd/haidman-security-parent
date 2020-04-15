package com.haidm.web.authentication;

import com.haidm.web.common.LoginTypeEnum;
import com.haidm.web.common.response.BaseRespose;
import com.haidm.web.common.response.ResponseEnum;
import com.haidm.web.common.response.ResponseUtils;
import com.haidm.web.properites.SecurityProperites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {


    @Autowired
    private SecurityProperites securityProperites;
    /**
     * 认证失败抛出异常
     * @param request
     * @param response
     * @param e
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        if(LoginTypeEnum.JSON.equals(securityProperites.getAuthentication().getLoginType())){
            BaseRespose result = ResponseUtils.makeToResp(ResponseEnum.ERROR.getRespCode(), e.getMessage());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        }else {
            super.setDefaultFailureUrl(securityProperites.getAuthentication().getLoginPage() + "?error");
            super.onAuthenticationFailure(request, response, e);
        }

    }
}
