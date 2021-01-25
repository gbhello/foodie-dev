package com.imooc.consts;

/**
 * @author gengbin
 * @date 2021/1/14
 */
public abstract class ValidationErrorCode {
    public static final String PARAM_EMPTY = "foodie.param.empty";
    public static final String INSTANCE_EXIST = "foodie.instance.exist";
    public static final String PASSWORD_LENGTH_ILLEGAL = "foodie.password.length.illegal";
    public static final String PASSWORD_DIFFERENT = "foodie.password.different";
    public static final String USERNAME_PASSWORD_DISMATCH = "foodie.username.password.dismatch";

    public class Address {
        public static final String RECEIVER_EMPTY = "foodie.receiver.empty";
        public static final String RECEIVER_TOO_LONG = "foodie.receiver.too.long";
        public static final String ADDRESS_EMPTY = "foodie.address.empty";
    }

    public class Mobile {
        public static final String MOBILE_EMPTY = "foodie.mobile.empty";
        public static final String MOBILE_ILLEGAL = "foodie.mobile.illegal";
    }
}
