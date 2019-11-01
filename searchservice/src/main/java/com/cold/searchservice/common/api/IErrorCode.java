package com.cold.searchservice.common.api;

/**
 * 封装API的错误码
 * Created by ohj on 2019/10/10.
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
