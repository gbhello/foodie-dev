package com.imooc.consts;

/**
 * @author gengbin
 * @date 2021/1/19
 */
public enum YesOrNo {
    NO(0, "否"),
    YES(1, "是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

}
