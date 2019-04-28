package com.niezhiliang.signature.utils.circle;


import com.niezhiliang.signature.utils.base.BaseSeal;
import com.niezhiliang.signature.utils.base.SealFactory;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/23 上午10:57
 */
public class CircleSealFactory extends SealFactory {

    @Override
    public BaseSeal getInstance() {
        if (this.seal == null) {
            return new CircleSeal();
        }
        return this.seal;
    }
}
