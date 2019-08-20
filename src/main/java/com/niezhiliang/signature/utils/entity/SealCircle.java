package com.niezhiliang.signature.utils.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Description: 印章圆圈类
 * @Author Ran.chunlin
 * @Date: Created in 15:41 2018-10-04
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SealCircle {
    /**
     * 线宽度
     */
    private Integer lineSize;
    /**
     * 半径
     */
    private Integer width;
    /**
     * 半径
     */
    private Integer height;
}
