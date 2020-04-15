package com.haidm.web.authentication.code;

import com.haidm.web.authentication.CustomAuthenticationFailureHandler;
import com.haidm.web.authentication.exception.ValidateCodeException;
import com.haidm.web.properites.SecurityProperites;
import com.haidm.web.util.ConstantUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shs-xxaqbzymhd
 */
@Component
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    private  SecurityProperites securityProperites;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 校验登录请求的验证码是否正确
        if(request.getRequestURI()
                .equals(securityProperites.getAuthentication().getLoginProcessingUrl())
        && request.getMethod().equalsIgnoreCase("post")){
            try {
                //校验验证码合法性
                validateCode(request);
            } catch (AuthenticationException e) {
                //交给失败处理器进行异常处理
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        filterChain.doFilter(request,response);
    }

    private void validateCode(HttpServletRequest request) {
        String sessionCode = (String)request.getSession()
                .getAttribute(ConstantUtils.SESSION_KEY_IMAGE_CODE);
        //获取用户输入的验证码
        String  code = (String) request.getParameter("code");
        if(StringUtils.isBlank(code)){
            throw new ValidateCodeException("验证码不能为空");
        }
        if(!code.equalsIgnoreCase(sessionCode)){
            throw new ValidateCodeException("验证码输入错误");
        }
    }
}
