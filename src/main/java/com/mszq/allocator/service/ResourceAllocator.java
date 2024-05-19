package com.mszq.allocator.service;

import com.mszq.allocator.common.vo.request.ResourceApplyVo;

/**
 * 资源分配器
 */
public interface ResourceAllocator {

    /**
     * 申请资源
     * @param resourceApplyVo 要申请的资源信息
     * @return 申请结果
     */
    Boolean allocate(ResourceApplyVo resourceApplyVo);
}
