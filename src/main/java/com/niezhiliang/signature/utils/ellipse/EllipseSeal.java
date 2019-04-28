package com.niezhiliang.signature.utils.ellipse;

import com.niezhiliang.signature.utils.base.BaseSeal;
import com.niezhiliang.signature.utils.circle.CircleSealFactory;
import com.niezhiliang.signature.utils.entity.SealCircle;
import com.niezhiliang.signature.utils.entity.SealConfiguration;
import com.niezhiliang.signature.utils.entity.SealDTO;
import com.niezhiliang.signature.utils.entity.SealFont;
import com.niezhiliang.signature.utils.utils.SealUtils;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/23 下午4:48
 */
public class EllipseSeal extends BaseSeal {

    @Override
    public String createSeal(SealDTO sealDTO) throws Exception {
        System.out.println("创建椭圆签章");
        SealConfiguration configuration = new SealConfiguration();
        /**
         * 主文字
         */
        SealFont mainFont = new SealFont();
        mainFont.setBold(true);
        mainFont.setFontFamily(sealDTO.getFont());
        mainFont.setMarginSize(10);
        /**************************************************/
        mainFont.setFontText(sealDTO.getCompanyName());
        mainFont.setFontSize(25);
        mainFont.setFontSpace(12.0);
        if (sealDTO.getCompanyName().length() > 14) {
            mainFont.setFontSize(20);
            mainFont.setFontSpace(8.0);
        }


        configuration.setMainFont(mainFont);

        /**
         * 副文字
         */
        if (sealDTO.getSerNo() != null && !"".equals(sealDTO.getSerNo())) {
            SealFont viceFont = new SealFont();
            viceFont.setBold(true);
            viceFont.setFontFamily(sealDTO.getFont());
            viceFont.setMarginSize(5);
            /**************************************************/
            viceFont.setFontText(sealDTO.getSerNo());
            viceFont.setFontSize(13);
            viceFont.setFontSpace(12.0);
            /***************************************************/

            configuration.setViceFont(viceFont);
        }


        /**
         * 抬头文字
         */
        if (sealDTO.getTitle() != null && !"".equals(sealDTO.getTitle())) {
            SealFont titleFont = new SealFont();
            titleFont.setBold(true);
            titleFont.setFontFamily(sealDTO.getFont());
            titleFont.setFontSize(22);
            if (sealDTO.getCompanyName().length() > 14) {
                titleFont.setFontSize(20);
            }
            /**************************************************/
            titleFont.setFontText(sealDTO.getTitle());
            titleFont.setMarginSize(68);
            titleFont.setMarginSize(27);

            configuration.setTitleFont(titleFont);
        }

        /**
         * 图片大小
         */
        configuration.setImageSize(300);
        /**
         * 背景颜色
         */
        configuration.setBackgroudColor(sealDTO.getColor());
        /**
         * 边线粗细、半径
         */
        configuration.setBorderCircle(new SealCircle(4, 140, 90));

        return SealUtils.buildAndStoreSeal(configuration);
    }
}
