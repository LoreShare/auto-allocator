package com.mszq.allocator.service;

import com.mszq.allocator.common.bean.Result;
import com.mszq.allocator.common.vo.request.ResourceApplyVo;

/**
 * 资源管理的Service
 */
public interface ResourceService {

    /**
     * 申请资源
     * @param resourceApplyVo 要申请的资源信息
     * @return 响应结果
     */
    Result<String> apply(ResourceApplyVo resourceApplyVo);
}
