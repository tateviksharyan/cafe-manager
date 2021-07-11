package com.cafe.manager.security.jwt;

import com.cafe.manager.security.jwt.domain.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class JWTAuthenticationService {

    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 hour
    private static final String SECRET = "gTTjxjlqsS1WxF10ZMcaZQbAfOvEl3g";
    private static final String AUTHORITIES = "Authorities";
    private static final String ID = "id";
    private static final String SUBJECT = "sub";
    private static final String TOKEN_PREFIX = "CM ";//just text to begin with p.s my nickname

    //Generating authentication header after successful login
    public static String generateAuthHeader(Authentication auth) {

        final List<String> authorities = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        final Map<String, Object> claims = new HashMap<>();

        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        claims.put(ID, String.valueOf(userPrincipal.getId()));
        claims.put(SUBJECT, userPrincipal.getUsername());
        claims.put(AUTHORITIES, authorities);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return TOKEN_PREFIX + jwt;
    }

    @SuppressWarnings("unchecked")
    //Checking auth header accuracy for requests, that should contain it
    public static Authentication parseAuthHeader(String authHeader) {
        String authToken = authHeader.replace(TOKEN_PREFIX, "");

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(authToken)
                .getBody();

        String username = claims.getSubject();

        if (username == null) {
            return null;
        }

        final List<String> authoritiesClaim = (List<String>) claims.get(AUTHORITIES);

        final List<SimpleGrantedAuthority> authorities = authoritiesClaim
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        long id = Long.parseLong(claims.get(ID, String.class));

        UserPrincipal principal = new UserPrincipal();
        principal.setId(id);
        principal.setUsername(username);
        principal.setAuthorities(authorities);

        return new UsernamePasswordAuthenticationToken(principal, null, authorities);
    }
}
