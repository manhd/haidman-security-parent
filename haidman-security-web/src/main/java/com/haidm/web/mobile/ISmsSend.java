package com.haidm.web.mobile;

/**
 * 短信发送统一接口
 * @author shs-xxaqbzymhd
 */
public interface ISmsSend {

    /**
     * 短信发送接口
     * @param mobile
     * @param content
     * @return
     */
    boolean sendSms(String mobile, String content);
}
