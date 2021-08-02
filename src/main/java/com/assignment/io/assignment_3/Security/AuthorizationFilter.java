package com.assignment.io.assignment_3.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorizationFilter extends BasicAuthenticationFilter {



    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/") || request.getServletPath().equals("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header =request.getHeader(SecurityConstants.HEADER_STRING);

//        System.out.println(request.getUserPrincipal().getName());
        System.out.println(request.getServletPath());

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            if (request.getServletPath().equals("/") || request.getServletPath().equals("/favicon.ico")){
                return ;
            }
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationFilter= getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationFilter);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token=request.getHeader(SecurityConstants.HEADER_STRING);

        if (token!=null){
            token=token.replace(SecurityConstants.TOKEN_PREFIX, "");

            try {


                Jws<Claims> claimsJws = Jwts.parser()
                        .setSigningKey(SecurityConstants.TOKEN_SECRET)
                        .parseClaimsJws(token);
                Claims body = claimsJws.getBody();

                String username = body.getSubject();

                List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");

                Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                        .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                        .collect(Collectors.toSet());
                return new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        simpleGrantedAuthorities
                );


            }catch (JwtException e){
                throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
            }

//            String user = Jwts.parser()
//                    .setSigningKey(SecurityConstants.getTokenSecret())
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//            if (user!=null){
//                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
//
//            }
//            return null;
        }
        return null;
    }

}
