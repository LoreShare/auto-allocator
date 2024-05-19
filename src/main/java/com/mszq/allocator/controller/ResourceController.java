package com.mszq.allocator.controller;

import com.mszq.allocator.common.bean.Result;
import com.mszq.allocator.common.vo.request.ResourceApplyVo;
import com.mszq.allocator.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 资源管理的Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/resource")
public class ResourceController {
    /**
     * 资源Service
     */
    private ResourceService resourceService;

    /**
     * 申请资源
     * @param resourceApplyVo 要申请的资源信息
     * @return 响应结果
     */
    @PostMapping("/apply")
    public Result<String> apply(@RequestBody ResourceApplyVo resourceApplyVo){
        return resourceService.apply(resourceApplyVo);
    }
}
