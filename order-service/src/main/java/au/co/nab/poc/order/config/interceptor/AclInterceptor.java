package au.co.nab.poc.order.config.interceptor;

import au.co.nab.poc.order.constant.CustomHttpHeaders;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author andy - tuantda.uit@gmail.com
 */
public class AclInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AclInterceptor.class);
    private static final String CREATE_VOUCHER_PATH = "/voucher";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();
        if (CREATE_VOUCHER_PATH.equalsIgnoreCase(requestPath)
                && HttpMethod.POST.name().equalsIgnoreCase(requestMethod)) {
            return true;
        }

        String phone = request.getHeader(CustomHttpHeaders.PHONE);
        if (StringUtils.isEmpty(phone)) {
            logger.error("Error: phone does not exist.");
            throw new Exception("Access denied.");
        }

        MDC.put(CustomHttpHeaders.PHONE, phone);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        MDC.clear();
    }

}