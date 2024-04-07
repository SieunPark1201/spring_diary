package com.example.spring_diary.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.Enumeration;


@Aspect
//@Component를 통해 Spring bean객체를 만든다. (bean객체는 싱글톤을 의미한다)
@Component
//로그 라이브러리인 log4J 라이브러리 사용
@Slf4j
public class LogService {

    //    @Around 을 통해 logging의 대상을 지정; 괄호 안에 있는 경로에 대해 아래 로직을 적용하겠다는 의미
    @Around("execution(* com.example.spring_board..controller..*.*(..))")
    public Object controllerLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        로그의 형태를 json으로 출력하기 위해 json객체 생성
        JSONObject jsonObject = new JSONObject();
//        (put이 제일 중요)
        jsonObject.put("method name", proceedingJoinPoint.getSignature().getName());   // key와 value
//        사용자의 request정보는 HttpServletRequest객체 안에 담겨 있으므로, 해당 객체에서 추출
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        jsonObject.put("CRUD name", req.getMethod());
        Enumeration<String> req_body = req.getParameterNames();
//        뒤죽박죽 나오므로 json객체를 하나 더 만들어서 그 안에 user정보를 담음. => 순차적으로 나오도록.
//        json은 이렇게 계층 구조를 짜기에 좋다(json 안에 json을 넣는다던가)
        JSONObject jsonObject_details = new JSONObject();
        while(req_body.hasMoreElements()){
            String body = req_body.nextElement();
//            jsonObject.put(body, req.getParameter(body));
            jsonObject_details.put(body, req.getParameter(body));
        }
        jsonObject.put("user inputs", jsonObject_details);
//        log4j 라이브러리를 통해 위에서 만든 jsonObject를 log로 출력
//        일반적으로 실무에서는 log를 콘솔이 아니라 파일로 영구저장되도록 관리한다
//        그런데, 정상적인 request까지 모두 log로 남기게 되면 파일시스템에 용량이 full차는 경우가 빈번하다
//        그래서 log.info 뿐만 아니라, log.trace, log. debug, log.info, log.error 등의 로그 레벨이 존재한다.
//        log.info("request info"+jsonObject);
//        log.error("request info"+jsonObject);

        return proceedingJoinPoint.proceed();
    }
}
