package com.imooc.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.consts.StatusCodeConsts;
import lombok.Data;

/**
 * 自定义响应数据结构
 * 本类可提供给H5/ios/android/公众号/小程序 使用
 *
 * @author gengbin
 * @date 2021/1/14
 */
@Data
public class CommonResult {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private Integer status;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应中的数据
     */
    private Object data;
    @JsonIgnore
    private String ok;

    public CommonResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(Integer status, String msg, Object data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;

    }

    public CommonResult(Object data) {
        this.status = StatusCodeConsts.SUCCESS;
        this.msg = "OK";
        this.data = data;
    }

    public static CommonResult build(Integer status, String msg, Object data) {
        return new CommonResult(status, msg, data);
    }

    public static CommonResult build(Integer status, String msg, Object data, String ok) {
        return new CommonResult(status, msg, data, ok);
    }

    public static CommonResult ok(Object data) {
        return new CommonResult(data);
    }

    public static CommonResult ok() {
        return new CommonResult(null);
    }

    public static CommonResult errorMsg(String msg) {
        return new CommonResult(StatusCodeConsts.ERROR, msg, null);
    }

    public static CommonResult errorMap(Object data) {
        return new CommonResult(StatusCodeConsts.CHECK_FAILED, "error", data);
    }

    public static CommonResult errorTokenMsg(String msg) {
        return new CommonResult(StatusCodeConsts.TOKEN_ERROR, msg, null);
    }

    public static CommonResult errorException(String msg) {
        return new CommonResult(StatusCodeConsts.EXCEPTION_ERROR, msg, null);
    }

    public static CommonResult errorUserQQ(String msg) {
        return new CommonResult(StatusCodeConsts.QQ_CHECK_ERROR, msg, null);
    }
}
