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
}
