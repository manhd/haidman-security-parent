package com.haidm.web.common.response;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseRespose<T> {

    private Integer respCode;

    private String respMsg;

    private T respInfo;

    public String  toJsonString(){
        return JSON.toJSONString(this);
    }
}
