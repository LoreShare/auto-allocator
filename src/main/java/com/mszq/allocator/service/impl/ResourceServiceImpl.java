package com.mszq.allocator.service.impl;

import com.mszq.allocator.common.bean.Result;
import com.mszq.allocator.common.vo.request.ResourceApplyVo;
import com.mszq.allocator.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 资源管理的Service实现类
 */
@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private NamespaceAllocator namespaceAllocator;
    /**
     * 申请资源
     * @param resourceApplyVo 要申请的资源信息
     * @return 响应结果
     */
    @Override
    public Result<String> apply(ResourceApplyVo resourceApplyVo) {
        return null;
    }
}
