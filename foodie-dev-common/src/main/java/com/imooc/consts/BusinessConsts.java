package com.imooc.consts;

/**
 * @author gengbin
 * @date 2021/1/14
 */
public abstract class BusinessConsts {
    /**
     * 密码长度
     */
    public static final Integer PASSWORD_LENGTH = 6;
    /**
     * 默认头像
     */
    public static final String DEFAULT_USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
    /**
     * 默认生日
     */
    public static final String DEFAULT_BIRTHDAY = "1900-01-01";
    /**
     * error等级时间
     */
    public static final Integer ERROR_TAKE_TIME = 3000;
    /**
     * warn等级时间
     */
    public static final Integer WARN_TAKE_TIME = 2000;
    /**
     * info等级时间
     */
    public static final Integer INFO_TAKE_TIME = 1000;

    public static final Integer DEFAULT_PAGE_SIZE = 10;
    /**
     * 收件人名称最大长度
     */
    public static final int MAX_RECEIVER_LENGTH = 12;


    /**
     * 支付回调地址
     */
    public static final String PAY_RETURN_URL = "http://api.z.mukewang.com/foodie-dev-api/orders/notifyMerchantOrderPaid";
    /**
     * 支付中心调用地址
     */
    public static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
    /**
     * 增加库存类型
     */
    public static final Integer INCREASE_ITEM_SPEC_STOCK_TYPE = 1;
    /**
     * 减少库存类型
     */
    public static final Integer DECREASE_ITEM_SPEC_STOCK_TYPE = 0;


    public static class Order {
        public static final String UNSUPPORT_PAY_TYPE = "支付方式不支持！";
        public static final Integer INCRESE_ITEM_SPEC_STOCK = 1;
        public static final Integer DECREASE_ITEM_SPEC_STOCK = 2;
    }
}
