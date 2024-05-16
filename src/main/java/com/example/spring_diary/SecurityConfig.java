package com.example.spring_diary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
//@EnableWebSecurity는 spring security를 customizing할 수 있는 기능을 활성화시켜준다
@EnableWebSecurity
public class SecurityConfig {

//    스프링에서 Bean(싱글톤)을 만드는 방법 두 가지
//       * 싱글톤: 그때그때 객체를 만드는 게 아니라 스프링이 기동되었을 때 객체를 하나만 만들고 돌려쓰는 것-메모리 절약을 위해
//    방법 1. @Component 방식
//      개발자가 직접 컨트롤이 가능한 내부 클래스에서 사용

//    방법 2. @Configuration + @Bean 방식
//      개발자가 직접 컨트롤할 수 없는 외부 라이브러리 적용 시 사용

    @Autowired
    private CustomAuthFailureHandler customAuthFailureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                //login authenticated에서 제외할 대상 url 지정
                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers(HttpMethod.GET, "/user/login", "/user/new").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/user/login","/user/new").permitAll()
                        .requestMatchers(HttpMethod.GET, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/user/login")
                        .loginProcessingUrl("/doLogin") //스프링에서 지정해둔 doLogin 그대로 활용(doLogout도 마찬가지)
                        .usernameParameter("email") //스프링에서 사용했던 변수 그대로 사용하여 명시해줘야 함
                        .passwordParameter("password")
                        .failureHandler(customAuthFailureHandler)
                        .successHandler(loginSuccessHandler)
                        //                로그인 성공시 이후 로직 LoginSuccessHandler에서 구현
                )
                .logout(logout -> logout
                        .logoutUrl("/doLogout")
                )
                .csrf(CsrfConfigurer::disable); // CSRF 보호 비활성화;

        return httpSecurity.build();
    }
}
