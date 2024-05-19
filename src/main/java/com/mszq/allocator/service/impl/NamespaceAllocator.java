package com.mszq.allocator.service.impl;

import com.mszq.allocator.common.vo.request.ResourceApplyVo;
import com.mszq.allocator.service.ResourceAllocator;
import org.springframework.stereotype.Service;

/**
 * 命名空间分配器
 */
@Service
public class NamespaceAllocator implements ResourceAllocator {
    @Override
    public Boolean allocate(ResourceApplyVo resourceApplyVo) {
        return null;
    }
}
