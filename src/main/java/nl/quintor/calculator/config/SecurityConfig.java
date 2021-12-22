package nl.quintor.calculator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

import javax.net.ssl.HttpsURLConnection;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {// @formatter:off
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/user/info", "/calculate").authenticated()
                .antMatchers(HttpMethod.POST, "/calculate").authenticated()
                .antMatchers(HttpMethod.GET, "/login/**", "/oauth/**", "/user").permitAll()
                .anyRequest()
                .authenticated().and()
                .oauth2Login().defaultSuccessUrl("http://localhost:8089");
//                );
    }//@formatter:on
//    .and().exceptionHandling(e -> e
//            .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//    .and().logout().invalidateHttpSession(true)
//                .clearAuthentication(true).logoutSuccessUrl("http://localhost:8089/").deleteCookies("JSESSIONID").permitAll().
}
