package com.haidm.security.properites;

import lombok.Data;

@Data
public class AuthenticationProperties {
    private String loginPage = "/login/page";
    private String loginProcessingUrl = "login/token";
    private String usernameParameter = "author";
    private String passwordParameter = "authorCode";
    private String[] staticPaths = {"/dist/**","/modules/**","/plugins/**"};



}
