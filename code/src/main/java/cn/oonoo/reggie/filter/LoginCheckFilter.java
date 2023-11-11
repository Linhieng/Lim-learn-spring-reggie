package cn.oonoo.reggie.filter;

import cn.oonoo.reggie.common.R;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    // 路径匹配器，支持根据通配符进行匹配
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("拦截到请求, {}", request.getRequestURI());

        // 1.
        String requestURI = request.getRequestURI();

        // 可以直接放行的请求路径
        String[] urlsEnable = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
        };

        // 2. 判断本次请求是否放行
        boolean match = check(urlsEnable, requestURI);

        // 3.
        if (match) {
            filterChain.doFilter(request, response);
            return;
        }

        // 4. 判断是否已登录
        if (request.getSession().getAttribute("employee") != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5.
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }


    /**
     * 判断 requestURI 是否包含在 urls 中
     * @param urls
     * @param requestURI
     * @return 如果包含，则返回 true，不包含则返回 false
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            if (PATH_MATCHER.match(url, requestURI)) {
                return true;
            }
        }
        return false;
    }

}
