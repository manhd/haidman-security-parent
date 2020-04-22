package com.haidm.web.authentication;

import com.haidm.web.common.LoginTypeEnum;
import com.haidm.web.common.response.BaseRespose;
import com.haidm.web.common.response.ResponseEnum;
import com.haidm.web.common.response.ResponseUtils;
import com.haidm.web.properites.SecurityProperites;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
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
//            super.setDefaultFailureUrl(securityProperites.getAuthentication().getLoginPage() + "?error");
            //获取上一次的请求路径
            String referer = request.getHeader("Referer");
            //如果下面有值，则认为是多端登录，直接返回登录认证页面，否则返回到上一次请求地址
            Object toAuthentication = request.getAttribute("toAuthentication");
            String lastUrl = toAuthentication !=null ?securityProperites.getAuthentication().getLoginPage()
                    :StringUtils.substringBefore(referer, "?");
            log.info("---------上一次请求路径【Referer:{}】,【lastUrl:{}】--------------------",referer,lastUrl);

            super.setDefaultFailureUrl(lastUrl+"?error");
            super.onAuthenticationFailure(request, response, e);
        }

    }
}
