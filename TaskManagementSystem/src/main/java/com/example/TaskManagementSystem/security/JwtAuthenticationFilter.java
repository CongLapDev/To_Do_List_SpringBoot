package com.example.TaskManagementSystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
// oncePerRequestFilter la 1 abtract class do spring cung cap no dam bao moi requset chi di qua 1 lan
    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws  ServletException, IOException {
        //lay token tu request header
        String token = getTokenFromRequest(request);

        //kiem tra token co hop le khong
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            //lay username tu token
            String username = tokenProvider.getUsernameFromToken(token);

            //load thong tin user tu DB
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //set thong tin user vao security -> bao cho spring biet user nay da xacs thuc
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        //cho phep request di tiep
        filterChain.doFilter(request, response);
    }
        private String getTokenFromRequest(HttpServletRequest request) {
            String bearerToken = request.getHeader("Authorization");
            //token thuong co dang bearer abcxyz...
            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);   //cat bo chu bearer de lay chuoi token
            }
        return null;
    }
}
