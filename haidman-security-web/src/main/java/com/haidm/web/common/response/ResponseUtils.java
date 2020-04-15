package com.haidm.web.common.response;

import com.alibaba.fastjson.JSON;

/**
 * @author shs-xxaqbzymhd
 */
public class ResponseUtils {


    /**
     * 成功返回-无数据
     * @return
     */
    public static BaseRespose makeToResp(){
        return new BaseRespose.BaseResposeBuilder()
                .respCode(ResponseEnum.SUCCESS.getRespCode())
                .respMsg(ResponseEnum.SUCCESS.getRespMsg())
                .build();
    }

    /**
     * 成功返回-有数据
     * @return
     */
    public static <T>BaseRespose<T> makeToResp(T data){
        return new BaseRespose.BaseResposeBuilder()
                .respCode(ResponseEnum.SUCCESS.getRespCode())
                .respMsg(ResponseEnum.SUCCESS.getRespMsg())
                .respInfo(data)
                .build();
    }

    /**
     * 自定义 ResponseEnum 返回-无数据
     * @return
     */
    public static BaseRespose makeToResp(ResponseEnum responseEnum){
        return new BaseRespose.BaseResposeBuilder()
                .respCode(responseEnum.getRespCode())
                .respMsg(responseEnum.getRespMsg())
                .build();
    }

    /**
     * 自定义 ResponseEnum 返回-有数据
     * @return
     */
    public static  <T>BaseRespose<T> makeToResp(ResponseEnum responseEnum, T data){
        return new BaseRespose.BaseResposeBuilder()
                .respCode(responseEnum.getRespCode())
                .respMsg(responseEnum.getRespMsg())
                .respInfo(data)
                .build();
    }

    /**
     * 自定义 返回信息 -无数据
     * @return
     */
    public static  BaseRespose makeToResp(Integer respCode, String respMsg){
        return new BaseRespose.BaseResposeBuilder()
                .respCode(respCode)
                .respMsg(respMsg)
                .build();
    }

    /**
     * 自定义 返回信息 返回-有数据
     * @return
     */
    public static  <T>BaseRespose<T> makeToResp(Integer respCode, String respMsg, T data){
        return new BaseRespose.BaseResposeBuilder()
                .respCode(respCode)
                .respMsg(respMsg)
                .respInfo(data)
                .build();
    }


    /**
     * 响应成功自定义 返回信息 返回-有数据
     * @return
     */
    public static  <T>BaseRespose<T> makeToResp(String respMsg, T data){
        return new BaseRespose.BaseResposeBuilder()
                .respCode(ResponseEnum.SUCCESS.getRespCode())
                .respMsg(respMsg)
                .respInfo(data)
                .build();
    }

    /**
     * 响应成功 自定义 返回信息 返回-无数据
     * @return
     */
    public static  BaseRespose makeToResp(String respMsg){
        return new BaseRespose.BaseResposeBuilder()
                .respCode(ResponseEnum.SUCCESS.getRespCode())
                .respMsg(respMsg)
                .build();
    }



}
