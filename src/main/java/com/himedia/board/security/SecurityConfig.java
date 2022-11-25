package com.himedia.board.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity //이 클래스롤부터 생성된 객체가 시큐리티 설정파일임을 의미하면서, 동시에 시큐리티를 사용한데 필요한 수많은 객체를 생성
public class SecurityConfig extends WebSecurityConfigurerAdapter { //WebSecurityConfigurerAdapter 클래스를 상속한 시큐리티 설정 클래스가 빈으로 등록되기만 하면 더 이상 애플리케이션에서는 로그인을 강제하지 않는다.

    @Autowired
    private SecurityUserDetailsService userDetailsService;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {  //HttpSecurity객체를 이용하여 인증과 인가를 제어할 수 있다.


//        SecurityContextHolder.getContext().setAuthentication(authenticationManager().authenticate());

        security.authorizeRequests().antMatchers("/h2-console/**").permitAll().and().csrf().ignoringAntMatchers("/h2-console/**").disable();

        //authorizeRequests() 사용자 인증과 권한을 설정    antMatchers("URL 패턴")

        security.userDetailsService(userDetailsService);
        security.authorizeRequests().antMatchers("/", "/system/**").permitAll();  //   '/'와 'system'으로 시작하는 경로에는 인증되지 않은 모든 사용자가 접근할 수 있다.
        security.authorizeRequests().antMatchers("/board/**").authenticated();  // /board로 시작하는 경로에는 인증된 사용자만 접근할 수 있다.
        security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN"); // /admin으로 시작하는 경로에는 ADMIN권한을 가진 사용자만 접근


        security.csrf().disable(); //csrf()크로스 사이트 위조 요청에 대한 설정, RESTfull을 사용하기 위해서는 csrf 기능을 비활성화해야함

        security.formLogin().loginPage("/system/login").defaultSuccessUrl("/board/getBoardList", true);  //로그인 성공시 /board/getBoardList



        security.exceptionHandling().accessDeniedPage("/system/accessDenied");
        security.logout().logoutUrl("/system/logout").invalidateHttpSession(true).logoutSuccessUrl("/");  // /system/logout 요청을 전송하면 세션을 강제 종료

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



//    @Autowired  //AuthenticationManagerBuilder 객체를 @Autowired를 통해 의존성 주입 받은 authenticate()에서는 인증에 필요한 사용자 정보를 생성
//    public void authenticate(AuthenticationManagerBuilder auth) throws Exception{
//        //{noop}은 비밀번호에 대한 암호화 처리를 하지 않겠다.
//        auth.inMemoryAuthentication().withUser("manager").password("{noop}manager123");
//
//        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin123").roles("ADMIN");
//
//    }










}
