package com.atguigu.common.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 生成JSON Web令牌的工具类
 */
public class JwtHelper {

    //设置token过期时间
    private static long tokenExpiration = 365 * 24 * 60 * 60 * 1000;
    //设置token密钥
    private static String tokenSignKey = "123456";

    //创建token
    public static String createToken(String  userId, String username) {
        String token = Jwts.builder()
                //设置分组
                .setSubject("AUTH-USER")
                //设置什么时候token过期
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                //有效载荷
                .claim("userId", userId)
                .claim("username", username)
                //HS512加密
                .signWith(SignatureAlgorithm.HS512, tokenSignKey)
                //压缩成一行
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    public static String getUserId(String token) {
        try {
            if (StringUtils.isEmpty(token)) return null;
            //设置密钥解密
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            //获取userId
            String userId = (String) claims.get("userId");
            return userId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUsername(String token) {
        try {
            if (StringUtils.isEmpty(token)) return "";

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            //获取信息
            return (String) claims.get("username");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void removeToken(String token) {
        //jwttoken无需删除，客户端扔掉即可。
    }

    public static void main(String[] args) {
        String token = JwtHelper.createToken("1", "admin");//"eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJSCjAK0A0Ndg1S0lFKrShQsjI0MzY2sDQ3MTbQUSotTi3yTFGyMjKEsP0Sc1OBWp6unfB0f7NSLQDxzD8_QwAAAA.2eCJdsJXOYaWFmPTJc8gl1YHTRl9DAeEJprKZn4IgJP9Fzo5fLddOQn1Iv2C25qMpwHQkPIGukTQtskWsNrnhQ";//JwtHelper.createToken(7L, "admin");
        System.out.println(token);
        String userId = JwtHelper.getUserId(token);
        //eyJhbGciOiJIUzUxMiIsInppcCI6IkdaSVAifQ.H4sIAAAAAAAAAKtWKi5NUrJScgwN8dANDXYNUtJRSq0oULIyNDc0Mjc3MTE10FEqLU4t8kwBqjJUgnDyEnNTgdzElNzMPKVaALNEuJZEAAAA.HKmZceErTT-vuhJfgVSN8HaNzBLgAjEVo2XfBpdAqzUD_phspn1GuMeVbG56-U4kpGWvjmajd3kWxSausiKeOw
        System.out.println(userId);
        String username = JwtHelper.getUsername(token);
        System.out.println(username);
//        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUsername(token));
    }
}