package com.assignment.io.assignment_3.Security;

import com.assignment.io.assignment_3.Config.Depend.ApplicationUserRole;
import com.assignment.io.assignment_3.Service.CustomerService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WbSecurity extends WebSecurityConfigurerAdapter {

    private final CustomerService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WbSecurity(CustomerService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf().disable()

               .authorizeRequests()

               .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
               .permitAll()

               .antMatchers(HttpMethod.GET, "/auth/checkTelNomer")
               .permitAll()
               .antMatchers(HttpMethod.GET, "/auth/image")
               .permitAll()
//               .antMatchers(HttpMethod.GET, "/")
//               .permitAll()


                .antMatchers("/admin/**").hasRole(ApplicationUserRole.ADMIN.name())
//               .antMatchers( "/users/**").hasRole(ApplicationUserRole.USER.name())

               .anyRequest()
               .authenticated()
               .and()

               .cors()
               .and()

               .addFilter(getAuthenticationFilter())
               .addFilter(new AuthorizationFilter(authenticationManager()))

               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

       ;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception{
        final AuthenticationFilter filter=new AuthenticationFilter(authenticationManager());
        filter.setFilterProcessesUrl("/auth/login");
//        filter.setPostOnly(true);
        return filter;
    }
}
