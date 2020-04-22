package com.haidm.web.authentication.session;


import com.haidm.web.common.response.BaseRespose;
import com.haidm.web.common.response.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.InvalidSessionStrategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当session 失效后的处理逻辑
 * @author shs-xxaqbzymhd
 */
public class CustomInvalidSessionStrategy implements InvalidSessionStrategy {
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request,HttpServletResponse response) throws IOException {

        //删除浏览器中cookie 的 jsessionid
        cancelCookie(request, response);

        BaseRespose baseRespose = ResponseUtils.makeToResp(HttpStatus.UNAUTHORIZED.value(), "登录超时，请重新登录");
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(baseRespose.toJsonString());
    }


    protected void cancelCookie(HttpServletRequest request,HttpServletResponse response){
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        response.addCookie(cookie);

    }

    private String getCookiePath(HttpServletRequest request){
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";

    }
}
