package com.mszq.allocator.common.bean;

import lombok.Data;

/**
 * 统一返回格式
 * @param <T> data的数据类型
 */
@Data
public class Result<T> {

    /**
     * 成功状态码
     */
    private static final String CODE_SUCCESS = "200";

    /**
     * 失败状态码:参数异常
     */
    private static final String PARAM_ERROR = "400";

    /**
     * 失败状态码:业务异常
     */
    private static final String BUSINESS_ERROR = "500";

    /**
     * 简易成功返回
     */
    private static Result<String> ok = new Result<>();

    static {
        ok.setCode(CODE_SUCCESS);
    }

    /**
     * 状态码
     */
    private String code;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    /**
     * 简易成功返回
     * @return 返回值
     */
    public static Result<String> ok() {
        return ok;
    }

    /**
     * 带数据的成功返回
     * @param data 返回数据
     */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<T>();
        result.setCode(CODE_SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 参数异常返回
     * @param msg 返回信息
     * @return 返回值
     */
    public static Result<String> paramErr(String msg) {
        Result<String> result = new Result<>();
        result.setCode(PARAM_ERROR);
        result.setMsg(msg);
        return result;
    }

    /**
     * 业务异常返回
     * @param msg 返回信息
     * @return 返回值
     */
    public static Result<String> businessErr(String msg) {
        Result<String> result = new Result<>();
        result.setCode(BUSINESS_ERROR);
        result.setMsg(msg);
        return result;
    }
}
