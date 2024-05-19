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

/**
 * 操作workspace的Client
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class WorkspaceClient {

    /**
     * 访问KubeSphere的baseUrl
     */
    @Value("${ks.api.server.base.url}")
    private String baseUrl;

    /**
     * KubeSphere的用户名
     */
    @Value("${ks.api.server.username}")
    private String username;

    /**
     * HTTP工具类
     */
    private final RestTemplate restTemplate;

    /**
     * 认证Client,获取Token
     */
    private final AuthenticationClient authenticationClient;


    /**
     * 创建命名空间
     * @param workspaceName 企业空间
     * @return 成功标识
     */
    public Boolean createWorkspace(String workspaceName) {
        //创建企业空间的url
        String url = baseUrl + "/kapis/tenant.kubesphere.io/v1alpha3/workspacetemplates";

        //请求头设置
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", authenticationClient.getToken());

        //封装request
        HttpEntity<String> request = getRequest(workspaceName, headers);

        //发起请求
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        //如果响应成功
        if (response.getStatusCode().value() == 200) {
            log.info("企业空间 {} 创建成功", workspaceName);
            return true;
        }else {
            log.info("企业空间 {} 创建失败", workspaceName);
            return false;
        }
    }

    /**
     * 构造请求
     * @param workspaceName 企业空间名称
     * @param headers 请求头
     * @return 请求
     */
    private HttpEntity<String> getRequest(String workspaceName, HttpHeaders headers) {
        //获取创建企业空间的模版
        String contentTemplate = KubeSphereRequestContent.WORKSPACE_CREATE_CONTENT_TEMPLATE.getValue();

        //content中替换的内容
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("{workspaceName}", workspaceName);
        replaceMap.put("{username}",username);

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
