package au.co.nab.poc.order.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Objects;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@Component
public class InterceptorServiceConfig extends WebMvcConfigurationSupport {

    private static final Logger logger = LoggerFactory.getLogger(InterceptorServiceConfig.class);

    /** The interceptor names */
    @Value("${interceptors.name}")
    private String[] interceptorNames;

    /** The base package */
    @Value("${interceptors.base-package}")
    private String basePackage;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (Objects.isNull(interceptorNames)) {
            return;
        }

        for (String interceptorName : interceptorNames) {
            try {
                registry.addInterceptor(createInterceptorInstance(String.format("%s.%s", basePackage, interceptorName)));
            } catch (ClassNotFoundException|IllegalAccessException|InstantiationException e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * The method to initialize a interceptor instance
     *
     * @param className
     * @return HandlerInterceptor
     */
    private HandlerInterceptor createInterceptorInstance(String className)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        HandlerInterceptor interceptor;
        Class classDefinition = Class.forName(className);
        interceptor = (HandlerInterceptor) classDefinition.newInstance();
        return interceptor;
    }
}
