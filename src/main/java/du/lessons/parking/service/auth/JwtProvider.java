package du.lessons.parking.service.auth;

import du.lessons.parking.repository.IUserDao;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider implements IJwtProvider {

    private final IUserDao userDao;

    @Value("${assm.app.jwtSecret}")
    private String jwtSecret;

    @Value("${assm.app.jwtExpiration}")
    private Integer jwtExpirationTime;

    @Autowired
    public JwtProvider(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String generateJwtToken(String login) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(login)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + jwtExpirationTime * 1000))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    // SignatureException
    // IllegalArgumentException
    @Override
    public Boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            System.out.println(String.format("Invalid JWT token. Message: %s", e));
        } catch (SignatureException e1) {
            System.out.println(String.format("Invalid JWT signature. Message: %s", e1));
        } catch (IllegalArgumentException e2) {
            System.out.println(String.format("Wrong token string. Message: %s", e2));
        }
        return false;
    }

    @Override
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
