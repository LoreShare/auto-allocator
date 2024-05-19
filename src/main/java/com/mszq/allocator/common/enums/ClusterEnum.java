package com.mszq.allocator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClusterEnum {
    APPLICATION_CLUSTER(1,"生产应用集群"),
    DMZ_CLUSTER(2,"生产DMZ集群"),
    TEST_CLUSTER(3,"测试集群");

    private final Integer clusterId;
    private final String clusterName;

}
