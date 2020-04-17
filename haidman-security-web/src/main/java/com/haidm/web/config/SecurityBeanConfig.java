package com.haidm.web.config;

import com.haidm.web.mobile.ISmsSend;
import com.haidm.web.mobile.impl.SmsCodeSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主要为容器中添加 bean 实例
 * @author shs-xxaqbzymhd
 */
@Configuration
public class SecurityBeanConfig {

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
}
