package com.haidm.security.config;

import com.haidm.security.properites.SecurityProperites;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * ctl + o 覆盖父类方法
 */
@Configuration
@EnableWebSecurity //开启springsecurity 的过滤器链
@Slf4j
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private SecurityProperites security;

    @Autowired
    private UserDetailsService customDetailsServiceImpl;


    /**
     * 认证管理器
     * 1.提供认证信息（提供用户名密码）
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码存储需要加密
        /*String password = passwordEncoder().encode("123456");
        log.info("使用passwordEncoder 加密后的password: "+password);
        auth.inMemoryAuthentication().withUser("haidman")
                .password(password).authorities("ADMIN");*/
        auth.userDetailsService(customDetailsServiceImpl);
    }

    /**
     * 当认证成功之后，springsecurity 会 重定向到上一下访问 的请求
     * 系统资源权限配置
     * 1. 配置被拦截的资源
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic()//采用 HttpBaseic 认证方式
        http.formLogin()//采用 formLogin 表单登录 认证方式
                .loginPage(security.getAuthentication().getLoginPage())
                .loginProcessingUrl(security.getAuthentication().getLoginProcessingUrl()) // 登录表单提交处理的url, 默认为 /login
                .usernameParameter(security.getAuthentication().getUsernameParameter()) // 默认为 username
                .passwordParameter(security.getAuthentication().getPasswordParameter()) // 默认为 password
                .and()
                .authorizeRequests()//认证请求
                .mvcMatchers(security.getAuthentication().getLoginPage()).permitAll() //在 认证所有请求之前放行登录页面请求
                .anyRequest().authenticated() //所有访问该应用的http 请求都要进行身份认证成功后才能访问
        ;
    }

    /**
     * 一般针对静态资源放行
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(security.getAuthentication().getStaticPaths());
    }
}
