package com.mszq.allocator.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 主要是获取KubeSphere的token
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationClient {

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
     * KubeSphere的密码
     */
    @Value("${ks.api.server.password}")
    private String password;

    /**
     * 访问KubeSphere的token
     */
    private static String token;

    /**
     * 想要实现在续期token时,不允许读,防止unauthorized
     */
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * HTTP工具类
     */
    private final RestTemplate restTemplate;

    /**
     * 获取token
     * @return token
     */
    public String getToken(){
        //获取token时加读锁,读锁是共享锁
        lock.readLock().lock();
        try{
            return token;
        }finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 续期token
     */
    @Scheduled(fixedDelay = 1,timeUnit = TimeUnit.HOURS)
    public void renewToken(){
        //加写锁
        lock.writeLock().lock();

        try{
            //url
            String url = baseUrl + "/oauth/token";

            //表单方式提交
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            //表单内容
            HttpEntity<MultiValueMap<String, String>> request = getMultiValueMapHttpEntity(headers);

            //发送请求
            ResponseEntity<String> response = restTemplate.postForEntity(url, request , String.class);

            //如果响应成功
            if (response.getStatusCode().value() == 200 && StringUtils.hasText(response.getBody())){
                //解析响应
                JsonObject responseObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
                String token_type = responseObject.get("token_type").getAsString();
                String access_token = responseObject.get("access_token").getAsString();

                //拼接为token
                token = token_type + " " + access_token;

                //打印成功日志
                log.info("KubeSphere的token{}成功",StringUtils.hasText(token) ? "续期" : "创建");
            }else {
                log.error("KubeSphere的token{}失败,响应为{}",StringUtils.hasText(token) ? "续期" : "创建",response);
            }

        }finally {
            lock.writeLock().unlock();
        }

    }

    /**
     * 构造 oauth/token 请求的表单内容
     * @param headers 请求头
     * @return 表单内容
     */
    private HttpEntity<MultiValueMap<String, String>> getMultiValueMapHttpEntity(HttpHeaders headers) {
        MultiValueMap<String, String> requestParams= new LinkedMultiValueMap<>();
        requestParams.add("grant_type", "password");
        requestParams.add("client_id", "kubesphere");
        requestParams.add("client_secret", "kubesphere");
        requestParams.add("username", username);
        requestParams.add("password", password);

        //构造请求
        return new HttpEntity<>(requestParams, headers);
    }
}
