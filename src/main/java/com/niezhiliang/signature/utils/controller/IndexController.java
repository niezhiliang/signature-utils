package com.niezhiliang.signature.utils.controller;

import com.niezhiliang.signature.utils.SealUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2018/12/19 下午5:30
 */
@RestController
public class IndexController {

    @RequestMapping(value = "index")
    public String index(int type) {
        String base64 = null;
        switch (type) {
            case 0:
                try {
                    base64 = SealUtils.companyCircleSeal("浙江杭州江干下沙某某某网络集团有限公司",1,0,"","");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    base64 = SealUtils.companyCircleSeal("浙江某某某网络集团有限公司",0,1,null,"XX专用");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    base64 = SealUtils.companyCircleSeal("浙江某某某网络集团有限公司",2,2,"1234567899876","XX专用");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                try {
                    base64 = SealUtils.companyEllipseSeal("浙江某某某网络集团有限公司",0,1,"1234567899876","XX专用");
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        return base64;
    }
}
