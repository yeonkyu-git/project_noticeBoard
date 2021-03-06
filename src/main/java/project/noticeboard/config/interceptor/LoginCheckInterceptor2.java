package project.noticeboard.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import project.noticeboard.config.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor2 implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);


        if (session != null && session.getAttribute(SessionConst.LOGIN_MEMBER) != null) {
            log.info("로그인한 회원이 로그인 창 및 회원가입 창으로 가려고 함 ");
            response.sendRedirect("/board");
        }
        return true;
    }
}
