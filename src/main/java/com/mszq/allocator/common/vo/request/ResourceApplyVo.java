package com.mszq.allocator.common.vo.request;

import com.mszq.allocator.common.enums.ClusterEnum;
import lombok.Data;

import java.util.List;

/**
 * 接受资源申请的VO
 */
@Data
public class ResourceApplyVo {

    /**
     * 应用名称,也就是要创建的namespace的名称
     */
    private String applicationName;

    /**
     * 所属团队,根据所属团队获取到对应的企业空间信息,我要把他存到数据库里
     */
    private Integer teamId;

    /**
     * 集群环境,这个应该不变,用枚举好了
     */
    private ClusterEnum cluster;

    /**
     * 每个Pod的CPU信息和内存信息
     */
    private List<PodInfo> podInfos;

    /**
     * 每个PV的存储容量
     */
    private List<Integer> capacities;

    /**
     * namespace下的人员
     */
    private List<Integer> ldapAccounts;

    @Data
    private static class PodInfo{
        private Integer cpu;
        private Integer memory;
    }
}
