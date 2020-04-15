package com.haidm.web.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.haidm.web.util.ConstantUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author shs-xxaqbzymhd
 */
@Controller
@Slf4j
public class MainController {


    @Autowired
    private DefaultKaptcha defaultKaptcha;




    @RequestMapping({"index","/",""})
    public String index(){
        log.info("-----------访问首页----------------");
        return "index";
    }

    @RequestMapping("/login/page")
    public String hello(){
        log.info("-----------访问登录页面----------------");
        return "login";
    }

    /**
     * 获取图形验证码
     * @param request
     * @param response
     */
    @RequestMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("-----------获取图形验证码-----------");
        //1.获取验证码字符串前台
        String code = defaultKaptcha.createText();
        log.info("-----------生成的验证码为： {}",code);
        //2.将获取到的字符串放到 session 中
        request.getSession().setAttribute(ConstantUtils.SESSION_KEY_IMAGE_CODE,code);
        //3. 获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        //4.将验证码图片写出去
        ServletOutputStream out = response. getOutputStream();
        ImageIO.write(image,"jpg",out);
    }
}
