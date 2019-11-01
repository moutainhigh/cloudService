package com.cold.servicegateway;

import com.cold.servicegateway.exception.JsonExceptionHandler;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import java.util.Collections;
import java.util.List;

import static org.springframework.web.cors.CorsConfiguration.ALL;

/**
 * 网关服务
 */
@SpringBootApplication
//@EnableZuulProxy
@EnableEurekaClient
public class ServicegatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicegatewayApplication.class, args);
    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/baidu")
//                        .uri("http://baidu.com:80/")
//                )
//                .route("websocket_route", r -> r.path("/apitopic1/**")
//                        .uri("ws://127.0.0.1:6605"))
//                .route(r -> r.path("/userapi3/**")
//                        //.filters(f -> f.addResponseHeader("X-AnotherHeader", "testapi3"))
//
//                        .uri("lb://SERVICEAPI/")
//                )
//                .build();
//    }


//    private static final String MAX_AGE = "18000L";
//
//    /**
//     * 跨域请求
//     * @return
//     */
//    @Bean
//    public WebFilter corsFilter() {
//        return (ServerWebExchange ctx, WebFilterChain chain) -> {
//            ServerHttpRequest request = ctx.getRequest();
//            if (!CorsUtils.isCorsRequest(request)) {
//                return chain.filter(ctx);
//            }
//            HttpHeaders requestHeaders = request.getHeaders();
//            ServerHttpResponse response = ctx.getResponse();
//            HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
//            HttpHeaders headers = response.getHeaders();
//            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, requestHeaders.getOrigin());
//            headers.addAll(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, requestHeaders.getAccessControlRequestHeaders());
//            if (requestMethod != null) {
//                headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, requestMethod.name());
//            }
//            headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
//            headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ALL);
//            headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
//            if (request.getMethod() == HttpMethod.OPTIONS) {
//                response.setStatusCode(HttpStatus.OK);
//                return Mono.empty();
//            }
//            return chain.filter(ctx);
//        };
//    }

    /**
     * 自定义异常处理[@@]注册Bean时依赖的Bean，会从容器中直接获取，所以直接注入即可
     * @param viewResolversProvider
     * @param serverCodecConfigurer
     * @return
     */
//    @Primary
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public ErrorWebExceptionHandler errorWebExceptionHandler(ObjectProvider<List<ViewResolver>> viewResolversProvider,
//                                                             ServerCodecConfigurer serverCodecConfigurer) {
//
//        JsonExceptionHandler jsonExceptionHandler = new JsonExceptionHandler();
//        jsonExceptionHandler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
//        jsonExceptionHandler.setMessageWriters(serverCodecConfigurer.getWriters());
//        jsonExceptionHandler.setMessageReaders(serverCodecConfigurer.getReaders());
//        System.out.println("Init Json Exception Handler Instead Default ErrorWebExceptionHandler Success");
//        return jsonExceptionHandler;
//    }
}
