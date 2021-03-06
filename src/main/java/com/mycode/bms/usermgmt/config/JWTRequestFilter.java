package com.mycode.bms.usermgmt.config;

import com.mycode.bms.usermgmt.service.UserMgmtService;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Data
@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTHelper jwtHelper;

    private final UserMgmtService userMgmtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = null;
        String jwt = null;

        if(Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtHelper.extractUsername(jwt);
        }

        if(Objects.nonNull(username) && SecurityContextHolder.getContext().getAuthentication()==null) {
            final UserDetails userDetails = userMgmtService.loadUserByUsername(username);
            if(jwtHelper.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken upat
                        = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
//            } else {
//                filterChain.doFilter(request, response);
//            }
        }
        filterChain.doFilter(request, response);
    }
}
