package com.mszq.allocator.config;

import com.mszq.allocator.common.bean.Result;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

/**
 * 全局异常处理
 * 只会捕获@Controller或者@RestController抛出的异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 具体的异常处理逻辑
     * @param request 请求
     * @param e 异常
     * @return 标准返回
     */
    @ExceptionHandler(Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
        //这是调用第三方服务异常,本系统中就是调用KubeSphere或者K8S异常
        if (e instanceof HttpClientErrorException httpClientErrorException){
            return Result.businessErr(httpClientErrorException.getMessage());
        }

        log.error("未知错误, URI: {}, HttpMethod: {}", request.getRequestURI(), request.getMethod(), e);
        return Result.businessErr("系统错误,请查看日志");
    }
}
