package com.mszq.allocator.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class WorkspaceClientTest {

    @Autowired
    private WorkspaceClient workspaceClient;

    @Test
    public void createWorkspace() throws Exception {
        Boolean flag = workspaceClient.createWorkspace("demo1");
        log.info("成功标志: {}",flag);
    }

}
