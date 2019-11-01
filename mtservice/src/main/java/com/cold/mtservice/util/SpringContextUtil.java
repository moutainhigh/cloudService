package com.cold.mtservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * @Auther: ohj
 * @Date: 2019/9/19 10:58
 * @Description:
 */
@Slf4j
@Component
public class SpringContextUtil implements ApplicationContextAware {
    @Autowired
    private static   ApplicationContext applicationContext;

    //@Autowired
    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        log.info("应用程序上下文 ： [{}]", "开始初始化");
        if(SpringContextUtil.applicationContext == null){
            SpringContextUtil.applicationContext  = applicationContext;
        }
        log.info("应用程序上下文 getId ： [{}]", applicationContext.getId());
        log.info("应用程序上下文 getApplicationName ： [{}]", applicationContext.getApplicationName());
        //  log.info("应用程序上下文 getAutowireCapableBeanFactory ： [{}]",applicationContext.getAutowireCapableBeanFactory());
        //  log.info("应用程序上下文 getDisplayName ： [{}]",applicationContext.getDisplayName());
        //  log.info("应用程序上下文 getParent ： [{}]",applicationContext.getParent());
        log.info("应用程序上下文 getStartupDate ： [{}]", applicationContext.getStartupDate());
        //  log.info("应用程序上下文 getEnvironment ： [{}]",applicationContext.getEnvironment());

        log.info("应用程序上下文 ： [{}]", "初始化完成");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}