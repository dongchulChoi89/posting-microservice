package com.choi.springboot.config.auth;

import com.choi.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

// When there is a method which meets the condition, HandlerMethodArgumentResolver returns a instance which the implementing class returns as parameter
@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
        private final HttpSession httpSession;

        @Override
        public boolean supportsParameter(MethodParameter parameter) { // decide whether a parameter is supported by this annotation
            boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null; // when there is "@LoginUser" annotation
            boolean isUserClass = SessionUser.class.equals(parameter.getParameterType()); // when the parameter type is SessionUser class
            return isLoginUserAnnotation && isUserClass;
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, // make an instance and returns it as parameter
                                      ModelAndViewContainer mavContainer,
                                      NativeWebRequest webRequest,
                                      WebDataBinderFactory binderFactory) throws Exception {
            return httpSession.getAttribute("user");
        }
}
