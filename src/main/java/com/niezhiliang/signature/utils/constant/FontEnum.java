package com.niezhiliang.signature.utils.constant;


/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/2/12 上午10:56
 */
public enum  FontEnum {

    SONGTI("宋体"),

    FANGZHENGHEITI("方正黑体"),

    KAITI("楷体");
    ;
    private String font;

    FontEnum(String font) {
        this.font = font;
    }

    public String getFont() {
        return font;
    }
}
