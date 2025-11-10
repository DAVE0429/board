package com.board.api.config;
import ch.qos.logback.core.util.StringUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.access-token-key}")
    private String accessTokenKey;

    private long tokenValidTime = 365 * 60 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        accessTokenKey = Base64.getEncoder().encodeToString(accessTokenKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String  userPk){ // JWT 토큰 생성
        Claims claims = Jwts.claims().setSubject(userPk);
        Date now = new Date();
        Key key = Keys.hmacShaKeyFor(accessTokenKey.getBytes(StandardCharsets.UTF_8));
        return "Bearer " + Jwts.builder()
                .setClaims(claims) // 유저 식별자 정보
                .setIssuedAt(now) // 발급 시각
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 만료 시각
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    @Transactional
    public Authentication getAuthentication(String token){ // Spring Security 인증 객체로 변환
        try{
            System.out.println("토큰에서 추출된 사용자: " + this.getUserPk(token));
            UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String getUserPk(String token){ // JWT에서 사용자 정보 추출
        Key key = Keys.hmacShaKeyFor(accessTokenKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // 유효한 서명인지 검증도 같이 수행
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request){ // HTTP 헤더에서 JWT 추출
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring("Bearer " .length());
        }
        return null;
    }

    public boolean validateToken(String jwtToken){ // 유효성(서명, 만료) 검증
        try{
            Key key = Keys.hmacShaKeyFor(accessTokenKey.getBytes(StandardCharsets.UTF_8));

            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);

            return !claims.getBody().getExpiration().before(new Date());

        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
