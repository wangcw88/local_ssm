package com.location.local;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public final class SpringTool implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringTool.applicationContext == null) {
            SpringTool.applicationContext = applicationContext;
//            System.out.println(
//                    "========ApplicationContext配置成功,在普通类可以通过调用ToolSpring.getAppContext()获取applicationContext对象,applicationContext="
//                            + applicationContext + "========");
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /***
     * 根据一个bean的类型获取配置文件中相应的bean
     */
    public static <T> T getBeanByClass(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }
}