package com.example.component;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.service.CustomUserDetailsService;
import com.example.util.Constants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    //Tạo giá trị token
    public String generateJwtToken(Authentication authentication) {

        CustomUserDetailsService userPrincipal = (CustomUserDetailsService) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .claim("role", userPrincipal.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, Constants.SIGNING_KEY)
                .compact();
    }

    public String generatePasswordResetToken(String userName) {
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date((new Date()).getTime() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS512, Constants.SIGNING_KEY)
                .compact();
        return token;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean hasTokenExpired(String token) {
        Claims claims= Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(token).getBody();
        Date tokenExpirationDate = claims.getExpiration();
        Date today = new Date();
        return tokenExpirationDate.before(today);
    }

    //Kiểm tra token với khoá bí mật
    public boolean validateJwtToken(String authToken) {
        try {
        	//Check
            Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token" + ex);
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token" + ex);
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token" + ex);
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty." + ex);
        }
        return false;
    }
}