package com.niezhiliang.signature.utils;


import org.apache.tomcat.util.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/12/10 上午9:54
 */
public class SealUtils {
    /**
     * 默认从10x10的位置开始画，防止左上部分画布装不下
     */
    private final static int INIT_BEGIN = 10;

    private final static Color [] colors = {Color.RED,Color.BLUE,Color.BLACK};

    private final static String [] fonts = {"宋体","方正黑体","楷体"};

    /**
     * 绘制圆弧形文字
     *
     * @param g2d 画笔
     * @param circleRadius 弧形半径
     * @param font 字体对象
     * @param isTop 是否字体在上部，否则在下部
     */
    private static void drawArcFont4Circle(Graphics2D g2d, int circleRadius, SealFont font, boolean isTop) {
        if (font == null) {
            return;
        }

        //1.字体长度
        int fontTextLen = font.getFontText().length();

        //2.字体大小，默认根据字体长度动态设定 TODO
        int fontSize = font.getFontSize() == null ? (55 - fontTextLen * 2) : font.getFontSize();

        //3.字体样式
        int fontStyle = font.isBold() ? Font.BOLD : Font.PLAIN;

        //4.构造字体
        Font f = new Font(font.getFontFamily(), fontStyle, fontSize);

        FontRenderContext context = g2d.getFontRenderContext();
        Rectangle2D rectangle = f.getStringBounds(font.getFontText(), context);

        //5.文字之间间距，默认动态调整
        double fontSpace;
        if (font.getFontSpace() != null) {
            fontSpace = font.getFontSpace();
        } else {
            if (fontTextLen == 1) {
                fontSpace = 0;
            } else {
                fontSpace = rectangle.getWidth() / (fontTextLen - 1) * 0.9;
            }
        }

        //6.距离外圈距离
        int marginSize = font.getMarginSize() == null ? INIT_BEGIN : font.getMarginSize();

        //7.写字
        double newRadius = circleRadius + rectangle.getY() - marginSize;
        double radianPerInterval = 2 * Math.asin(fontSpace / (2 * newRadius));

        double fix = 0.04;
        if (isTop) {
            fix = 0.18;
        }
        double firstAngle;
        if (!isTop) {
            if (fontTextLen % 2 == 1) {
                firstAngle = Math.PI + Math.PI / 2 - (fontTextLen - 1) * radianPerInterval / 2.0 - fix;
            } else {
                firstAngle = Math.PI + Math.PI / 2 - ((fontTextLen / 2.0 - 0.5) * radianPerInterval) - fix;
            }
        } else {
            if (fontTextLen % 2 == 1) {
                firstAngle = (fontTextLen - 1) * radianPerInterval / 2.0 + Math.PI / 2 + fix;
            } else {
                firstAngle = (fontTextLen / 2.0 - 0.5) * radianPerInterval + Math.PI / 2 + fix;
            }
        }

        for (int i = 0; i < fontTextLen; i++) {
            double theta;
            double thetaX;
            double thetaY;

            if (!isTop) {
                theta = firstAngle + i * radianPerInterval;
                thetaX = newRadius * Math.sin(Math.PI / 2 - theta);
                thetaY = newRadius * Math.cos(theta - Math.PI / 2);
            } else {
                theta = firstAngle - i * radianPerInterval;
                thetaX = newRadius * Math.sin(Math.PI / 2 - theta);
                thetaY = newRadius * Math.cos(theta - Math.PI / 2);
            }

            AffineTransform transform;
            if (!isTop) {
                transform = AffineTransform.getRotateInstance(Math.PI + Math.PI / 2 - theta);
            } else {
                transform = AffineTransform.getRotateInstance(Math.PI / 2 - theta + Math.toRadians(8));
            }
            Font f2 = f.deriveFont(transform);
            g2d.setFont(f2);
            g2d.drawString(font.getFontText().substring(i, i + 1), (float) (circleRadius + thetaX + INIT_BEGIN),
                    (float) (circleRadius - thetaY + INIT_BEGIN));
        }
    }

    /**
     * 绘制椭圆弧形文字
     *
     * @param g2d 画笔
     * @param circle 外围圆
     * @param font 字体对象
     * @param isTop 是否字体在上部，否则在下部
     */
    private static void drawArcFont4Oval(Graphics2D g2d, SealCircle circle, SealFont font, boolean isTop) {
        if (font == null) {
            return;
        }
        float radiusX = circle.getWidth();
        float radiusY = circle.getHeight();
        float radiusWidth = radiusX + circle.getLineSize();
        float radiusHeight = radiusY + circle.getLineSize();

        //1.字体长度
        int fontTextLen = font.getFontText().length();

        //2.字体大小，默认根据字体长度动态设定
        int fontSize = font.getFontSize() == null ? 25 + (10 - fontTextLen) / 2 : font.getFontSize();

        //3.字体样式
        int fontStyle = font.isBold() ? Font.BOLD : Font.PLAIN;

        //4.构造字体
        Font f = new Font(font.getFontFamily(), fontStyle, fontSize);

        //5.总的角跨度
        float totalArcAng = 180;//(float) (font.getFontSpace() * fontTextLen);
        if (!isTop)
            totalArcAng = 120;

        //6.从边线向中心的移动因子
        float minRat = 0.90f;

        double startAngle = isTop ? -90f - totalArcAng / 2f : 90f - totalArcAng / 2f;
        double step = 0.5;
        int alCount = (int) Math.ceil(totalArcAng / step) + 1;
        double[] angleArr = new double[alCount];
        double[] arcLenArr = new double[alCount];
        int num = 0;
        double accArcLen = 0.0;
        angleArr[num] = startAngle;
        arcLenArr[num] = accArcLen;
        num++;
        double angR = startAngle * Math.PI / 180.0;
        double lastX = radiusX * Math.cos(angR) + radiusWidth;
        double lastY = radiusY * Math.sin(angR) + radiusHeight;
        for (double i = startAngle + step; num < alCount; i += step) {
            angR = i * Math.PI / 180.0;
            double x = radiusX * Math.cos(angR) + radiusWidth, y = radiusY * Math.sin(angR) + radiusHeight;
            accArcLen += Math.sqrt((lastX - x) * (lastX - x) + (lastY - y) * (lastY - y));
            angleArr[num] = i;
            arcLenArr[num] = accArcLen;
            lastX = x;
            lastY = y;
            num++;
            //System.out.println("i: "+i + "  accArcLen:"+accArcLen);
        }
        double arcPer = accArcLen / fontTextLen;
        for (int i = 0; i < fontTextLen; i++) {
            double arcL = i * arcPer + arcPer / 2.0;
            double ang = 0.0;
            for (int p = 0; p < arcLenArr.length - 1; p++) {
                if (arcLenArr[p] <= arcL && arcL <= arcLenArr[p + 1]) {
                    ang = (arcL >= ((arcLenArr[p] + arcLenArr[p + 1]) / 2.0)) ? angleArr[p + 1] : angleArr[p];
                    //System.out.println("i: "+i+" p:"+p );
                    break;
                }
            }
            angR = (ang * Math.PI / 180f);
           // System.out.println("haha："+i + ": "+angR);
            Float x = radiusX * (float) Math.cos(angR) + radiusWidth;
            Float y = radiusY * (float) Math.sin(angR) + radiusHeight;
            double qxang = Math.atan2(radiusY * Math.cos(angR), -radiusX * Math.sin(angR));
            double fxang = qxang + Math.PI / 2.0;

            //System.out.println("i:"+i + "----"+qxang+"-----"+fxang);

            int subIndex = isTop ? i : fontTextLen - 1 - i;
            String c = font.getFontText().substring(subIndex, subIndex + 1);

            //获取文字高宽
            FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(f);
            int w = fm.stringWidth(c), h = fm.getHeight();

            if (isTop) {
                x += h * minRat * (float) Math.cos(fxang);
                y += h * minRat * (float) Math.sin(fxang);
                x += -w / 2f * (float) Math.cos(qxang);
                y += -w / 2f * (float) Math.sin(qxang);
            } else {
                x += (h * minRat ) * (float) Math.cos(fxang);
                y += (h * minRat) * (float) Math.sin(fxang);
                x += w / 2f * (float) Math.cos(qxang);
                y += w / 2f * (float) Math.sin(qxang);
            }
            //System.out.println("i:"+i+"--x: "+x + "  y:"+y);

            // 旋转
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.scale(0.8, 1);

            if (isTop) {
                affineTransform.rotate(Math.toRadians((fxang * 180.0 / Math.PI - 90)), 0, 0);
                System.out.println("i:"+i + " ---" +(Math.toRadians((fxang * 180.0 / Math.PI - 90))));
            }

            else
                affineTransform.rotate(Math.toRadians((fxang * 180.0 / Math.PI + 180 - 90)), 0, 0);
            Font f2 = f.deriveFont(affineTransform);
            g2d.setFont(f2);
            g2d.drawString(c, x.intValue() + INIT_BEGIN, y.intValue() + INIT_BEGIN);
        }
    }
    /**
     * 生成印章图片
     *
     * @param conf 配置文件
     *
     * @return BufferedImage对象
     *
     * @throws Exception 异常
     */
    private static BufferedImage buildSeal(SealConfiguration conf) throws Exception {

        //1.画布
        BufferedImage bi = new BufferedImage(conf.getImageSize(), conf.getImageSize(), BufferedImage.TYPE_4BYTE_ABGR);

        //2.画笔
        Graphics2D g2d = bi.createGraphics();

        //2.1抗锯齿设置
        //文本不抗锯齿，否则圆中心的文字会被拉长
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        //其他图形抗锯齿
        hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(hints);

        //2.2设置背景透明度
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 0));

        //2.3填充矩形
        g2d.fillRect(0, 0, conf.getImageSize(), conf.getImageSize());

        //2.4重设透明度，开始画图
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC, 1));

        //2.5设置画笔颜色
        g2d.setPaint(conf.getBackgroudColor());

        //3.画边线圆
        if (conf.getBorderCircle() != null) {
            drawCicle(g2d, conf.getBorderCircle(), INIT_BEGIN, INIT_BEGIN);
        } else {
            throw new Exception("BorderCircle can not null！");
        }

        int borderCircleWidth = conf.getBorderCircle().getWidth();
        int borderCircleHeight = conf.getBorderCircle().getHeight();

        //6.画弧形主文字
        if (borderCircleHeight != borderCircleWidth) {
            drawArcFont4Oval(g2d, conf.getBorderCircle(), conf.getMainFont(), true);
        } else {
            drawArcFont4Circle(g2d, borderCircleHeight, conf.getMainFont(), true);
        }

        //7.画弧形副文字
        if (borderCircleHeight != borderCircleWidth) {
            drawArcFont4Oval(g2d, conf.getBorderCircle(), conf.getViceFont(), false);
        } else {
            drawArcFont4Circle(g2d, borderCircleHeight, conf.getViceFont(), false);
        }

        //8.画中心字
        drawFont(g2d, (borderCircleWidth + INIT_BEGIN) * 2, (borderCircleHeight + INIT_BEGIN) * 2,
                conf.getCenterFont());

        //9.画抬头文字
        drawFont(g2d, (borderCircleWidth + INIT_BEGIN) * 2, (borderCircleHeight + INIT_BEGIN) * 2, conf.getTitleFont());

        g2d.dispose();
        return bi;
    }


    /**
     * 画文字
     *
     * @param g2d 画笔
     * @param circleWidth 边线圆宽度
     * @param circleHeight 边线圆高度
     * @param font 字体对象
     */
    private static void drawFont(Graphics2D g2d, int circleWidth, int circleHeight, SealFont font) {
        if (font == null) {
            return;
        }

        //1.字体长度
        int fontTextLen = font.getFontText().length();

        //2.字体大小，默认根据字体长度动态设定
        int fontSize = font.getFontSize() == null ? (55 - fontTextLen * 2) : font.getFontSize();

        //3.字体样式
        int fontStyle = font.isBold() ? Font.BOLD : Font.PLAIN;

        //4.构造字体
        Font f = new Font(font.getFontFamily(), fontStyle, fontSize);
        g2d.setFont(f);

        FontRenderContext context = g2d.getFontRenderContext();
        String[] fontTexts = font.getFontText().split("\n");
        if (fontTexts.length > 1) {
            int y = 0;
            for (String fontText : fontTexts) {
                y += Math.abs(f.getStringBounds(fontText, context).getHeight());
            }
            //5.设置上边距
            float marginSize = INIT_BEGIN + (float) (circleHeight / 2 - y / 2);
            for (String fontText : fontTexts) {
                Rectangle2D rectangle2D = f.getStringBounds(fontText, context);
                g2d.drawString(fontText, (float) (circleWidth / 2 - rectangle2D.getCenterX() + 1), marginSize);
                marginSize += Math.abs(rectangle2D.getHeight());
            }
        } else {
            Rectangle2D rectangle2D = f.getStringBounds(font.getFontText(), context);
            //5.设置上边距，默认在中心
            float marginSize = font.getMarginSize() == null ?
                    (float) (circleHeight / 2 - rectangle2D.getCenterY()) :
                    (float) (circleHeight / 2 - rectangle2D.getCenterY()) + (float) font.getMarginSize();
            g2d.drawString(font.getFontText(), (float) (circleWidth / 2 - rectangle2D.getCenterX() + 1), marginSize);
        }
    }


    /**
     * 画圆
     *
     * @param g2d 画笔
     * @param circle 圆配置对象
     */
    private static void drawCicle(Graphics2D g2d, SealCircle circle, int x, int y) {
        if (circle == null) {
            return;
        }

        //1.圆线条粗细默认是圆直径的1/35
        int lineSize = circle.getLineSize() == null ? circle.getHeight() * 2 / (35) : circle.getLineSize();

        //2.画圆
        g2d.setStroke(new BasicStroke(lineSize));
        g2d.drawOval(x, y, circle.getWidth() * 2, circle.getHeight() * 2);
    }

    /**
     * 生成印章图片，并保存到指定路径
     *
     * @param conf 配置文件F
     *
     * @throws Exception 异常
     */
    public static String buildAndStoreSeal(SealConfiguration conf) throws Exception {
        return  "data:image/png;base64,"+Base64.encodeBase64String(buildBytes(buildSeal(conf)));
    }
    /**
     * 生成印章图片的byte数组
     *
     * @param image BufferedImage对象
     *
     * @return byte数组
     *
     * @throws IOException 异常
     */
    private static byte[] buildBytes(BufferedImage image) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            //bufferedImage转为byte数组
            ImageIO.write(image, "png", outStream);
            return outStream.toByteArray();
        }

    /**
     *
     * @param companyName 15字以内体验较佳多余15字 字体会变小
     * @param color 0 红色 1.蓝色 2.蓝色
     * @param font 0.宋体 1.方正黑体 2.楷体
     * @param serNo 最少4位最多20位
     * @param title 最多8位
     * @return
     * @throws Exception
     */
    public static String companyEllipseSeal(String companyName,int color,int font,String serNo,String title) throws Exception {

        String fontFaiily = font >= 0 && font < fonts.length ? fonts[font] : fonts[0];
        Color backgroudColor = color >= 0 && color < colors.length ? colors[color] : colors[0];

        SealConfiguration configuration = new SealConfiguration();
        /**
         * 主文字
         */
        SealFont mainFont = new SealFont();
        mainFont.setBold(true);
        mainFont.setFontFamily(fontFaiily);
        mainFont.setMarginSize(10);
        /**************************************************/
        mainFont.setFontText(companyName);
        mainFont.setFontSize(25);
        mainFont.setFontSpace(12.0);
        if (companyName.length() > 14) {
            mainFont.setFontSize(20);
            mainFont.setFontSpace(8.0);
        }


        configuration.setMainFont(mainFont);

        /**
         * 副文字
         */
        if (serNo != null && !"".equals(serNo)) {
            SealFont viceFont = new SealFont();
            viceFont.setBold(true);
            viceFont.setFontFamily(fontFaiily);
            viceFont.setMarginSize(5);
            /**************************************************/
            viceFont.setFontText(serNo);
            viceFont.setFontSize(13);
            viceFont.setFontSpace(12.0);
            /***************************************************/

            configuration.setViceFont(viceFont);
        }


        /**
         * 抬头文字
         */
        if (title != null && !"".equals(title)) {
            SealFont titleFont = new SealFont();
            titleFont.setBold(true);
            titleFont.setFontFamily(fontFaiily);
            titleFont.setFontSize(22);
            if (companyName.length() > 14) {
                titleFont.setFontSize(20);
            }
            /**************************************************/
            titleFont.setFontText(title);
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
        configuration.setBackgroudColor(backgroudColor);
        /**
         * 边线粗细、半径
         */
        configuration.setBorderCircle(new SealCircle(4, 140, 100));

        return SealUtils.buildAndStoreSeal(configuration);

    }


    public static String companyCircleSeal(String companyName,int color,int font,String serNo,String title) throws Exception {

        String fontFaiily = font >= 0 && font < fonts.length ? fonts[font] : fonts[0];
        Color backgroudColor = color >= 0 && color < colors.length ? colors[color] : colors[0];

        SealConfiguration configuration = new SealConfiguration();
        /**
         * 主文字
         */
        SealFont mainFont = new SealFont();
        mainFont.setBold(true);
        mainFont.setFontFamily(fontFaiily);
        mainFont.setMarginSize(5);
        mainFont.setFontText(companyName);
        mainFont.setFontSize(30);
        mainFont.setFontSpace(30.0);
        if (companyName.length() > 14) {
            mainFont.setFontSize(23);
            mainFont.setFontSpace(21.0);
        }

        configuration.setMainFont(mainFont);

        /**
         * 副文字
         */
        if (serNo != null && !"".equals(serNo)) {
            SealFont viceFont = new SealFont();
            viceFont.setFontFamily(fontFaiily);
            viceFont.setMarginSize(-5);
            /**************************************************/
            viceFont.setFontText(serNo);
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
        centerFont.setFontFamily(fontFaiily);
        centerFont.setFontText("★");
        centerFont.setFontSize(70);

        configuration.setCenterFont(centerFont);

        /**
         * 抬头文字
         */
        if (title != null && !"".equals(title)) {
            SealFont titleFont = new SealFont();
            titleFont.setBold(true);
            titleFont.setFontFamily(fontFaiily);
            titleFont.setFontSize(20);
            titleFont.setFontText(title);
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
        configuration.setBackgroudColor(backgroudColor);
        /**
         * 边线粗细、半径
         */
        configuration.setBorderCircle(new SealCircle(4, 115, 115));

        return SealUtils.buildAndStoreSeal(configuration);
    }

    public static void main(String[] args) {
        try {
            //System.out.println(companyEllipseSeal("浙江葫芦娃网络集团有限公司",1,2,"1234567899876","合同专用章"));
            System.out.println(companyCircleSeal("浙江葫芦娃网络集团有限公司哈哈哈哈撒地方",1,2,"",""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
