package com.haidm.web.properites;

import com.haidm.web.common.LoginTypeEnum;
import lombok.Data;

@Data
public class AuthenticationProperties {

    // 初始化值
    private String loginPage = "/login/page";
    private String loginProcessingUrl = "login/token";
    private String usernameParameter = "author";
    private String passwordParameter = "authorCode";
    private String[] staticPaths = {"/dist/**","/modules/**","/plugins/**"};
    private LoginTypeEnum loginType = LoginTypeEnum.REDIRECT;

    private String imageCodeUrl = "/code/image"; // 获取图形验证码url
    private String mobileCodeUrl = " /code/mobile"; // 获取手机验证码url
    private String mobilePageUrl = " /mobile/page"; // 跳转手机登录页面url
    private Integer tokenValiditySeconds= 259200;
}
