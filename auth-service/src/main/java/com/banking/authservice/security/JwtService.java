package com.banking.authservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

     private final  String JWT_SECRET ="mysecretkeymysecretkeymysecretkey12345";

      public String generateToken(String email) {
          return Jwts.builder()
                  .setSubject(email)
                  .setIssuedAt(new Date())
                  .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24))
                  .signWith(SignatureAlgorithm.HS256, JWT_SECRET.getBytes())
                  .compact();

      }

      public String extractUsername(String token) {
          return Jwts.parser()
                  .setSigningKey(JWT_SECRET)
                  .parseClaimsJwt(token)
                  .getBody()
                  .getSubject();

      }

       public boolean validateToken(String token) {
          try{

              Jwts.parser()
              .setSigningKey(JWT_SECRET)
               .parseClaimsJwt(token);
               return true;
          }catch(Exception e)
          {
              return false;
          }
       }
}
