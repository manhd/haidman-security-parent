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
 * 校验 用户输入的手机验证码是否正确
 * @author shs-xxaqbzymhd
 */
@Component
public class MobileValidateFilter extends OncePerRequestFilter {

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 判断请求是否为为手机登录，并且是  post 请求
        if("/mobile/form".equals(request.getRequestURI())
                && "post".equalsIgnoreCase(request.getMethod())){
            try {
                //校验验证码合法性
                validateCode(request);
            } catch (AuthenticationException e) {
                //交给失败处理器进行异常处理
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }
        //放行
        filterChain.doFilter(request,response);
    }

    private void validateCode(HttpServletRequest request) {
        String sessionCode = (String)request.getSession()
                .getAttribute(ConstantUtils.SESSION_KEY_MOBILE_CODE);
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
