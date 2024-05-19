package com.mszq.allocator.api;

import com.mszq.allocator.common.enums.KubeSphereRequestContent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResourceQuotaClient {

    /**
     * 访问KubeSphere的baseUrl
     */
    @Value("${ks.api.server.base.url}")
    private String baseUrl;

    /**
     * HTTP工具类
     */
    private final RestTemplate restTemplate;

    /**
     * 认证Client,获取Token
     */
    private final AuthenticationClient authenticationClient;

    public Boolean createStorageQuota(String namespace, Integer storageCapacity){
        //创建企业空间的url
        String url = baseUrl + "/api/v1/namespaces/"+ namespace +"/resourcequotas";

        //请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", authenticationClient.getToken());

        //封装request
        HttpEntity<String> request = getRequest(namespace, storageCapacity, headers);

        //发起请求
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        //如果响应成功
        if (response.getStatusCode().value() == 201) {
            log.info("命名空间 {} 下 最大存储限制{}GB的ResourceQuota 创建成功", namespace,storageCapacity);
            return true;
        }else {
            log.info("命名空间 {} 下 最大存储限制{}GB的ResourceQuota 创建失败", namespace,storageCapacity);
            return false;
        }
    }

    /**
     * 构造请求
     * @param namespace 企业空间名称
     * @param storageCapacity 命名空间的最大存储容量
     * @param headers 请求头
     * @return 请求
     */
    private HttpEntity<String> getRequest(String namespace, Integer storageCapacity, HttpHeaders headers) {
        //获取创建企业空间的模版
        String contentTemplate = KubeSphereRequestContent.STORAGE_QUOTA_CREATE_CONTENT_TEMPLATE.getValue();

        //content中替换的内容
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{namespace}", namespace);
        replaceMap.put("{storageCapacity}", String.valueOf(storageCapacity));

        //替换模版中的占位符
        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            contentTemplate = contentTemplate.replace(entry.getKey(), entry.getValue());
        }

        //请求体
        String requestBody = contentTemplate;

        //构造请求
        return new HttpEntity<>(requestBody, headers);
    }
}
