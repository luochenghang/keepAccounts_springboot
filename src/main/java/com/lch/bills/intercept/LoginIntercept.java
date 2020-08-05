package com.lch.bills.intercept;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lch.bills.common.*;
import com.lch.bills.jwt.CheckResult;
import com.lch.bills.jwt.JwtUtils;
import com.lch.bills.utils.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.asn1.ocsp.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;


public class LoginIntercept implements HandlerInterceptor {

    private final String ACCESS_TOKEN = "token";
    private Logger logger = LoggerFactory.getLogger(LoginIntercept.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("preHandle");
        //设置编码格式
        response.setCharacterEncoding("UTF-8");
        //这句话是解决乱码的
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Object action = handlerMethod.getBean();
        if (isAnonymous(handlerMethod)) {
            return true;
        }

        if (!(action instanceof BaseController)) {
            throw new Exception("异常");
        }


        String accessToken = CookieUtil.getAccessTokenFromRequest(request, ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) { //没有登录的情况下的判断
            response.getWriter().write(new ObjectMapper()
                    .writeValueAsString(AjaxResponse.toReturn(ResponseCode.NOLOGIN,ResponseCode.NOLOGIN.getMessage())));


            return false;
        }
//        CheckAuthServiceRequest checkAuthRequest = new CheckAuthServiceRequest();
//        checkAuthRequest.setToken(accessToken);
//        CheckAuthServiceResponse ru = userCoreService.checkAuth(checkAuthRequest);

        String uid = null;
        CheckResult checkResult = JwtUtils.validateJWT(accessToken);

        if (ServiceResponseCodeEnum.SYS_SUCCESS.getCode().equals(checkResult.getCode())) {
            //((BaseController) action).setUid(checkResult.getUid());
            return true;
        }

        response.getWriter().write(new ObjectMapper()
                .writeValueAsString(AjaxResponse.toReturn(ResponseCode.NOLOGIN,ResponseCode.NOLOGIN.getMessage())));
        return false;
    }

    private boolean isAnonymous(HandlerMethod handlerMethod) {
        Object action = handlerMethod.getBean();
        Class clazz = action.getClass();
        if (clazz.getAnnotation(Anonymous.class) != null) {
            return true;
        }
        Method method = handlerMethod.getMethod();
        return method.getAnnotation(Anonymous.class) != null;
    }
}
