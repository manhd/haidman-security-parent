package com.haidm.web.common.response;

import com.alibaba.fastjson.JSON;

/**
 * @author shs-xxaqbzymhd
 */

public enum ResponseEnum {
    /**
     * 请求成功响应
     */
    SUCCESS(0,"成功"),
    /**
     * 请求失败响应
     */
    FAILE(1,"失败"),
    /**
     * 服务异常响应
     */
    ERROR(9999,"服务请求异常，请联系管理员");

    private Integer respCode;
    private String respMsg;

    ResponseEnum(int respCode, String respMsg){
        this.respCode = respCode;
        this.respMsg = respMsg;
    }


    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
