package du.lessons.parking.service.auth;

import du.lessons.parking.lib.exceptions.UserNotFoundException;
import du.lessons.parking.model.User;
import du.lessons.parking.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private IJwtProvider provider;

    @Autowired
    private IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        /* Request:
                Headers: [
                    Authorization: Bearer [token]
                    ....
                ]
                ...
         */
        try {
            String authHeader = req.getHeader("Authorization");
            // Bearer [token]
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.replace("Bearer ", "");
            }

            if (token != null && provider.validateJwtToken(token)) {
                String login = provider.getUserNameFromJwtToken(token);
                UserDetails user = userService.findUserByLogin(login, false);
                UsernamePasswordAuthenticationToken springAuthToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                springAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(springAuthToken);
            }

        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(req, resp);
    }
}
