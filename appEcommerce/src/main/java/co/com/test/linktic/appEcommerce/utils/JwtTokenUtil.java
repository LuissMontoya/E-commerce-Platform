package co.com.test.linktic.appEcommerce.utils;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	 private String secretKey = "mySecretKey";

	    public String generateToken(String email) {
	        return Jwts.builder()
	                .setSubject(email)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas de validez
	                .signWith(SignatureAlgorithm.HS256, secretKey)
	                .compact();
	    }

	    public Claims getClaimsFromToken(String token) {
	        return Jwts.parser()
	                .setSigningKey(secretKey)
	                .parseClaimsJws(token)
	                .getBody();
	    }

	    public boolean isTokenValid(String token) {
	        return !isTokenExpired(token);
	    }

	    private boolean isTokenExpired(String token) {
	        Date expiration = getClaimsFromToken(token).getExpiration();
	        return expiration.before(new Date());
	    }
}
