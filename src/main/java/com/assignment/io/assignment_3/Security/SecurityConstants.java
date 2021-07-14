package com.assignment.io.assignment_3.Security;


import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


public class SecurityConstants {

    public static final long EXPIRATION_TIME=864000000;  //10 days
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
    public static final String SIGN_UP_URL="/auth/signUp";

//    @Value("${token.secret}")
    public static String TOKEN_SECRET="qurbanovulugbek428@gmail.com";
}
