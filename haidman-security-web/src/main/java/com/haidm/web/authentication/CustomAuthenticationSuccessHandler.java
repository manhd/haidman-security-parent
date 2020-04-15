package com.haidm.web.authentication;

import com.haidm.web.common.LoginTypeEnum;
import com.haidm.web.common.response.BaseRespose;
import com.haidm.web.common.response.ResponseUtils;
import com.haidm.web.properites.SecurityProperites;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  认证成功处理器
 *  1.决定认证成功之后响应json 还是跳转页面，或者成功之后进行其他的处理
 * @author shs-xxaqbzymhd
 */
@Component
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {


    @Autowired
    private SecurityProperites securityProperites;

    /**
     * 认证成功 响应  json
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
        HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //认证成功响应 json 字符串
        if(LoginTypeEnum.JSON.equals(securityProperites.getAuthentication().getLoginType())){
            BaseRespose result = ResponseUtils.makeToResp("认证成功");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result.toJsonString());
        }else {
            super.onAuthenticationSuccess(request, response, authentication);
        }

    }
}
