package com.niezhiliang.signature.utils.base;


import com.niezhiliang.signature.utils.entity.SealDTO;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/23 下午4:37
 */
public abstract class BaseSeal implements Seal {

    public abstract String createSeal(SealDTO sealDTO) throws Exception;
}
