package com.niezhiliang.signature.utils.entity;

import lombok.Data;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/23 下午5:18
 */
@Data
public class SealDTO {
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 颜色
     */
    private Integer color = 0;

    /**
     * 字体
     */
    private Integer font = 0;

    /**
     * 下弦文
     */
    private String serNo;

    /**
     * 副标题
     */
    private String title;

    /**
     * 颜色的集合
     */
    private static List<Color> colorList = Arrays.asList(Color.RED,Color.BLUE,Color.BLACK);

    /**
     * 字体集合
     */
    private static List<String> fonts = Arrays.asList("宋体","方正黑体","楷体");

    public Color getColor() {
        return color > colorList.size() ? colorList.get(0) : colorList.get(color);
    }

    public String getFont() {
        return font > fonts.size() ? fonts.get(0) : fonts.get(font);
    }
}
