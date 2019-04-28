package com.niezhiliang.signature.utils.circle;


import com.niezhiliang.signature.utils.base.BaseSeal;
import com.niezhiliang.signature.utils.entity.SealCircle;
import com.niezhiliang.signature.utils.entity.SealConfiguration;
import com.niezhiliang.signature.utils.entity.SealDTO;
import com.niezhiliang.signature.utils.entity.SealFont;
import com.niezhiliang.signature.utils.utils.SealUtils;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/23 上午11:00
 */
public class CircleSeal extends BaseSeal {

    @Override
    public String createSeal(SealDTO sealDTO) throws Exception {
        System.out.println("创建圆形签章");
            SealConfiguration configuration = new SealConfiguration();
            /**
             * 主文字
             */
            SealFont mainFont = new SealFont();
            mainFont.setBold(true);
            mainFont.setFontFamily(sealDTO.getFont());
            mainFont.setMarginSize(5);
            mainFont.setFontText(sealDTO.getCompanyName());
            mainFont.setFontSize(30);
            mainFont.setFontSpace(30.0);
            if (sealDTO.getCompanyName().length() > 14) {
                mainFont.setFontSize(23);
                mainFont.setFontSpace(21.0);
            }

            configuration.setMainFont(mainFont);

            /**
             * 副文字
             */
            if (sealDTO.getSerNo() != null && !"".equals(sealDTO.getSerNo())) {
                SealFont viceFont = new SealFont();
                viceFont.setFontFamily(sealDTO.getFont());
                viceFont.setMarginSize(-5);
                /**************************************************/
                viceFont.setFontText(sealDTO.getSerNo());
                viceFont.setBold(false);
                viceFont.setFontSize(13);
                viceFont.setFontSpace(10.0);
                /**************************************************/

                configuration.setViceFont(viceFont);
            }

            /**
             * 中心文字
             */
            SealFont centerFont = new SealFont();
            centerFont.setBold(false);
            centerFont.setFontFamily(sealDTO.getFont());
            centerFont.setFontText("★");
            centerFont.setFontSize(70);

            configuration.setCenterFont(centerFont);

            /**
             * 抬头文字
             */
            if (sealDTO.getTitle() != null && !"".equals(sealDTO.getTitle())) {
                SealFont titleFont = new SealFont();
                titleFont.setBold(true);
                titleFont.setFontFamily(sealDTO.getFont());
                titleFont.setFontSize(20);
                titleFont.setFontText(sealDTO.getTitle());
                titleFont.setMarginSize(70);

                configuration.setTitleFont(titleFont);
            }

            /**
             * 图片大小
             */
            configuration.setImageSize(250);
            /**
             * 背景颜色
             */
            configuration.setBackgroudColor(sealDTO.getColor());
            /**
             * 边线粗细、半径
             */
            configuration.setBorderCircle(new SealCircle(4, 115, 115));

            return SealUtils.buildAndStoreSeal(configuration);
    }
}
