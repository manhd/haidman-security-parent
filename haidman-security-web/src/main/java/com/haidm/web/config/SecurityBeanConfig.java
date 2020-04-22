package com.haidm.web.config;

import com.haidm.web.authentication.session.CustomInvalidSessionStrategy;
import com.haidm.web.authentication.session.CustomSessionInformationExpiredStrategy;
import com.haidm.web.mobile.ISmsSend;
import com.haidm.web.mobile.impl.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 主要为容器中添加 bean 实例
 * @author shs-xxaqbzymhd
 */
@Configuration
public class SecurityBeanConfig {


    /**
     * 多端登录session 处理
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CustomSessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
        return new CustomSessionInformationExpiredStrategy();
    }

    /**
     * @ConditionalOnMissingBean(ISmsSend.class)
     * 默认情况下，采用的是smsCodeSender 实例
     * 但是如果容器中存在其他的SmsSemd 类型的实例
     * 那当前的这个 SmsCodeSender 就失效了
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(ISmsSend.class)
    public ISmsSend iSmsSend(){
        return new SmsCodeSender();
    }


    /**
     * 当session 失效后的处理类
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(CustomInvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(){
        return new CustomInvalidSessionStrategy();
    }
}
