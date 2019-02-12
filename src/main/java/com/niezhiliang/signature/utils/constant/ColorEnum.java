package com.niezhiliang.signature.utils.constant;

import java.awt.*;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/2/12 上午10:51
 */
public enum ColorEnum {

    RED(Color.RED, "红色"),

    BLUE(Color.BLUE, "蓝色"),

    BLACK(Color.BLACK,"黑色");
    ;
    private Color color;

    private String message;

    ColorEnum(Color color, String message) {
        this.color = color;
        this.message = message;
    }

    public Color getColor() {
        return color;
    }

    public String getMessage() {
        return message;
    }
}
