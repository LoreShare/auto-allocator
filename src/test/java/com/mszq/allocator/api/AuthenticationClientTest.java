package com.mszq.allocator.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class AuthenticationClientTest {

    @Autowired
    private AuthenticationClient authenticationClient;

    @Test
    public void renewToken(){
        authenticationClient.renewToken();
        String token = authenticationClient.getToken();
        log.info(token);
    }
}
