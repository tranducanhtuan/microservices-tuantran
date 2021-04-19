package au.co.nab.poc.gateway.filter;

import au.co.nab.poc.gateway.constant.CustomHttpHeaders;
import au.co.nab.poc.gateway.entity.AuthenticatedUser;
import au.co.nab.poc.gateway.entity.UserDetail;
import com.netflix.zuul.context.RequestContext;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author andy - tuantda.uit@gmail.com
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(requestTokenHeader)) {
            logger.warn("Request without token.");
            chain.doFilter(request, response);
            return;
        }

        if (!validateToken(requestTokenHeader)) {
            logger.warn("JWT Token is invalid.");
            throw new ServletException("Access denied.");
        }

        AuthenticatedUser authenticatedUser = getUserFromJwt(requestTokenHeader);
        addUserInformationToHeader(authenticatedUser);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                new UserDetail(authenticatedUser.getPhone()), null, new ArrayList<>()
        );

        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        chain.doFilter(request, response);
    }

    final String USER_1_TOKEN = "0909492488";
    final String USER_2_TOKEN = "0971668930";
    final List<String> MOCK_TOKEN_LIST = Arrays.asList(USER_1_TOKEN, USER_2_TOKEN);
    /**
     * Now just mock up that jwtToken is user phone number
     * And matching with mock-up user phone in H2 DB.
     * TODO: use JWT lib & secretKey to validate Token
     * */
    private boolean validateToken(String jwtToken) {
        if (MOCK_TOKEN_LIST.stream().anyMatch(token -> token.equals(jwtToken))) {
            return true;
        }
        return false;
    }

    /**
     * Now just mock-up that jwtToken is user phone number.
     * TODO: use JWT lib & to get user data from payload & map to AuthenticatedUser
     * */
    private AuthenticatedUser getUserFromJwt(String jwtToken) {
        return new AuthenticatedUser(null, null, null, null, jwtToken);
    }

    private void addUserInformationToHeader(AuthenticatedUser authenticatedUser) {
        RequestContext.getCurrentContext()
                .addZuulRequestHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        RequestContext.getCurrentContext()
                .addZuulRequestHeader(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());
        RequestContext.getCurrentContext()
                .addZuulRequestHeader(CustomHttpHeaders.PHONE, authenticatedUser.getPhone());
    }

}