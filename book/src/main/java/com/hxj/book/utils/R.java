package com.hxj.book.utils;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class R {

    private Integer code;
    private String msg;
    private Object data;

    public static R ok(Object data){
        return new R().setCode(20000).setData(data);
    }

    public static R fail(String msg){
        return new R().setCode(40000).setMsg(msg);
    }

    public static R build(Integer code, String msg, Object data){
        return new R().setCode(code).setMsg(msg).setData(data);
    }
}
