package com.niezhiliang.signature.utils.base;


import com.niezhiliang.signature.utils.entity.SealDTO;

/**
 * @Author NieZhiLiang
 * @Email nzlsgg@163.com
 * @Date 2019/4/23 上午10:54
 */
public interface Seal {

    String createSeal(SealDTO sealDTO) throws Exception;
}
