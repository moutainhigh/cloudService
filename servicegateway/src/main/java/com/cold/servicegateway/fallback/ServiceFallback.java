package com.cold.servicegateway.fallback;

import com.alibaba.fastjson.JSONObject;
//import com.netflix.hystrix.exception.HystrixTimeoutException;
//import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * @Auther: ohj
 * @Date: 2019/9/19 08:30
 * @Description:熔断处理,服务高可用
 */
@Component
public class ServiceFallback{

//    @Override
//    public String getRoute() {
//        // TODO Auto-generated method stub
//        return "*";//service id ,如果想要支持所有的就return "*" or return null;
//    }
//
//    @Override
//    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
//        if (cause instanceof HystrixTimeoutException) {
//            return response(HttpStatus.GATEWAY_TIMEOUT);
//        } else {
//            return this.fallbackResponse();
//        }
//    }
//
//    public ClientHttpResponse fallbackResponse() {
//        return this.response(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    private ClientHttpResponse response(final HttpStatus status) {
//        return new ClientHttpResponse() {
//
//            @Override
//            public HttpStatus getStatusCode() throws IOException {
//                return status;
//            }
//
//            @Override
//            public int getRawStatusCode() throws IOException {
//                return status.value();
//            }
//
//            @Override
//            public String getStatusText() throws IOException {
//                return status.getReasonPhrase();
//            }
//
//            @Override
//            public void close() {
//            }
//
//            @Override
//            public InputStream getBody() throws IOException {
//                String result = "服务不可用，请稍后再试。"+getStatusCode();
////                return new ByteArrayInputStream(result.getBytes());
//                JSONObject returnObject = new JSONObject();
//                returnObject.put("code", 500);
//                returnObject.put("msg", result);
//                returnObject.put("time", System.currentTimeMillis());
//                return new ByteArrayInputStream(returnObject.toJSONString().getBytes("utf-8"));
//            }
//
//            @Override
//            public HttpHeaders getHeaders() {
//                // headers设定
//                HttpHeaders headers = new HttpHeaders();
//                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
//                headers.setContentType(mt);
//                return headers;
//            }
//        };
//    }
}
