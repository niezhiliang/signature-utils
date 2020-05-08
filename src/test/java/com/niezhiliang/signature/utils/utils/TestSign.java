package com.niezhiliang.signature.utils.utils;

import com.niezhiliang.signature.utils.base.BaseSeal;
import com.niezhiliang.signature.utils.circle.CircleSealFactory;
import com.niezhiliang.signature.utils.ellipse.EllipseSealFactory;
import com.niezhiliang.signature.utils.entity.SealDTO;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @GitHub https://github.com/niezhiliang
 * @Date 2019-08-20 15:44
 */
public class TestSign {

    @Test
    public void testCircleSign() throws Exception {
        SealDTO sealDTO = new SealDTO();
        sealDTO.setCompanyName("浙江葫芦娃网络集团有限公司");
        sealDTO.setSerNo("123456789987");
        sealDTO.setTitle("财务专用章");
        sealDTO.setColor(2);
        sealDTO.setFont(2);
        BaseSeal baseSeal =  new CircleSealFactory().getInstance();
        Files.write(Paths.get("/Users/fxq/Desktop/signature-utils/imgs/1.png"), Base64.getDecoder().decode(baseSeal.createSeal(sealDTO).replace("data:image/png;base64,","")), StandardOpenOption.CREATE);
    }

    @Test
    public void testEllipseign() throws Exception {
        SealDTO sealDTO = new SealDTO();
        sealDTO.setCompanyName("浙江葫芦娃网络集团有限公司哈哈");
        sealDTO.setSerNo("123456789987");
        sealDTO.setTitle("财务专用章");
        BaseSeal baseSeal =  new EllipseSealFactory().getInstance();
        Files.write(Paths.get("/Users/fxq/Desktop/signature-utils/imgs/2.png"), Base64.getDecoder().decode(baseSeal.createSeal(sealDTO).replace("data:image/png;base64,","")), StandardOpenOption.CREATE);
    }

}
