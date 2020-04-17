package com.haidm.web.controller;

import com.haidm.web.common.response.BaseRespose;
import com.haidm.web.common.response.ResponseUtils;
import com.haidm.web.mobile.ISmsSend;
import com.haidm.web.util.ConstantUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 手机登录以及涉及的控制层
 * @author shs-xxaqbzymhd
 */
@Controller
public class MobileController {


    @Autowired
    private ISmsSend iSmsSend;


    /**
     * 跳转手机手机登录的页面
     * @return
     */
    @RequestMapping("/mobile/page")
    public String toMobilePage(){
        return "login-mobile";
    }

    /**
     * 发送手机验证码
     * @return
     */
    @RequestMapping("/code/mobile")
    @ResponseBody
    public BaseRespose smsCode(HttpServletRequest request){
        //1.生成一个6位数的短信验证码
        String code = RandomStringUtils.randomNumeric(6);
        //2.将短信验证码保存到 session 中
        request.getSession().setAttribute(ConstantUtils.SESSION_KEY_MOBILE_CODE,code);
        //3. 调用短信发送接口
        String mobile = request.getParameter("mobile");
        iSmsSend.sendSms(mobile, code);
        return ResponseUtils.makeToResp(code);
    }
}
