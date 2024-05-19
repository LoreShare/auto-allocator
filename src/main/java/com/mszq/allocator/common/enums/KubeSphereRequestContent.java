package com.mszq.allocator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum KubeSphereRequestContent {

    /**
     * 创建企业空间
     */
    WORKSPACE_CREATE_CONTENT_TEMPLATE("{\"apiVersion\":\"tenant.kubesphere.io/v1alpha2\",\"kind\":\"WorkspaceTemplate\",\"metadata\":{\"name\":\"{workspaceName}\",\"annotations\":{\"kubesphere.io/creator\":\"{username}\"}},\"spec\":{\"template\":{\"spec\":{\"manager\":\"{username}\"},\"metadata\":{\"annotations\":{\"kubesphere.io/creator\":\"{username}\"}}}}}"),

    /**
     * 创建命名空间的存储容量大小限制
     */
    STORAGE_QUOTA_CREATE_CONTENT_TEMPLATE("{\"apiVersion\":\"v1\",\"kind\":\"ResourceQuota\",\"metadata\":{\"name\":\"demo1\",\"namespace\":\"demo\",\"cluster\":\"default\",\"annotations\":{\"kubesphere.io/creator\":\"admin\"}},\"spec\":{\"hard\":{\"requests.storage\": \"5Gi\"}}}");

    private final String value;
}
