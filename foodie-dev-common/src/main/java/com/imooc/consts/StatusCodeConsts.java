package com.imooc.consts;

/**
 * @author gengbin
 * @date 2021/1/14
 */
public class StatusCodeConsts {
    /**
     * 成功
     */
    public static final int SUCCESS = 200;
    /**
     * 错误，错误信息在msg中
     */
    public static final int ERROR = 500;
    /**
     * bean验证错误，以map的形式返回
     */
    public static final int CHECK_FAILED = 501;
    /**
     * 拦截器拦截到用户token出错
     */
    public static final int TOKEN_ERROR = 502;
    /**
     * 异常抛出信息
     */
    public static final int EXCEPTION_ERROR = 555;
    /**
     * 用户qq校验异常
     */
    public static final int QQ_CHECK_ERROR = 556;
}
