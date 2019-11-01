package com.cold.searchservice.common.utils;

/**
 * @Auther: ohj
 * @Date: 2019/9/30 10:11
 * @Description:
 */
import java.util.HashMap;

public class Result extends HashMap<String, Object> {

    public Result() {
        put("code", 200);
        put("msg", "success");
    }

    public static Result error() {
        return error(500, "未知异常");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.put("msg", msg);
        return result;
    }

    public static Result success() {
        return new Result();
    }

    /**
     * 使用示例：Result put = Result.success().put(new Date());
     * @param value
     * @return
     */
    public Result put(Object value) {
        return put("data", value);
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}