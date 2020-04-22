package com.haidm.web.config;

import com.haidm.web.authentication.code.ImageCodeValidateFilter;
import com.haidm.web.authentication.code.MobileValidateFilter;
import com.haidm.web.properites.SecurityProperites;
import com.haidm.web.authentication.service.impl.CustomUserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * @author shs-xxaqbzymhd
 */
@Configuration
@Slf4j
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private SecurityProperites security;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;

    /**
     * 会话超时处理
     */
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    /**
     * 当用户超多设定数量的实现类
     */
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;




    /**
     * 记住我功能
     * @return
     */
    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        //是否启动项目自动创建表
        //jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 认证管理器
     * 1. 提供认证信息
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //密码加密
       /* String  password = passwordEncoder().encode("123123");
        //密码存储需要加密
        auth.inMemoryAuthentication()
                .withUser("root")
                .password(password)
                .authorities("admin");*/
       auth.userDetailsService(customUserDetailsService);
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
        http
                .addFilterBefore(mobileValidateFilter,UsernamePasswordAuthenticationFilter.class)
                // 添加过滤器   图形验证码过滤器 在 用户请求 认证之前
                .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                //采用 formLogin 表单登录 认证方式
                .formLogin()
                .loginPage(security.getAuthentication().getLoginPage())
                // 登录表单提交处理的url, 默认为 /login
                .loginProcessingUrl(security.getAuthentication().getLoginProcessingUrl())
                // 默认为 username
                .usernameParameter(security.getAuthentication().getUsernameParameter())
                // 默认为 password
                .passwordParameter(security.getAuthentication().getPasswordParameter())
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                //认证请求
                .authorizeRequests()
                //在 认证所有请求之前放行登录页面请求
                .mvcMatchers(security.getAuthentication().getLoginPage(),
                        /*,"/code/image", "/mobile/page", "/code/mobile"*/
                        security.getAuthentication().getImageCodeUrl(),
                        security.getAuthentication().getMobileCodeUrl(),
                        security.getAuthentication().getMobilePageUrl()

                ).permitAll()
                //所有访问该应用的http 请求都要进行身份认证成功后才能访问
                .anyRequest().authenticated()
                .and()
                //记住我功能 配置
                .rememberMe()
                //保存登录信息
                .tokenRepository(jdbcTokenRepository())
                //记录信息保存时时长 一天
                .tokenValiditySeconds(security.getAuthentication().getTokenValiditySeconds())
                .and()
                //session 管理
                .sessionManagement()
                //当session 失效后的处理类
                .invalidSessionStrategy(invalidSessionStrategy)
                //每个用户在系统中最多可以生成几个session
                .maximumSessions(1)
                .expiredSessionStrategy(sessionInformationExpiredStrategy)
                //当一个用户session 达到最大值，则不允许继续登录
                .maxSessionsPreventsLogin(true)
                //关闭csrf 跨站请求
                .and().and().csrf().disable()
        ;
        //将手机认证添加到过滤器链上
        http.apply(mobileAuthenticationConfig);
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
