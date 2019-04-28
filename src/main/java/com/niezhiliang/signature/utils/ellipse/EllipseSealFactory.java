package com.niezhiliang.signature.utils.ellipse;


import com.niezhiliang.signature.utils.base.BaseSeal;
import com.niezhiliang.signature.utils.base.SealFactory;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/23 上午10:56
 */
public class EllipseSealFactory extends SealFactory {

    @Override
    public BaseSeal getInstance() {
        if (this.seal == null) {
            return new EllipseSeal();
        }
        return this.seal;
    }
}
