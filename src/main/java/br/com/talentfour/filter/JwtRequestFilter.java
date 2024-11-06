package br.com.talentfour.filter;

import br.com.talentfour.config.JwtConfigProperties;
import br.com.talentfour.exception.StandardError;
import br.com.talentfour.exception.UnauthorizedException;
import br.com.talentfour.util.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtConfigProperties jwtConfigProperties;

    private final UserDetailsService userDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");
        final String requestURI = request.getRequestURI();

        if (jwtConfigProperties.getOpenUrls().contains(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwt);
            } catch (Exception e) {
                errorResponse(request, response, new UnauthorizedException("Token inválido ou expirado"));
                return;
            }
        }

        if (username == null && SecurityContextHolder.getContext().getAuthentication() == null) {
            errorResponse(request, response, new UnauthorizedException("Token inválido ou expirado"));
            return;
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtTokenUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                errorResponse(request, response, new UnauthorizedException("Token inválido ou expirado"));
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private void errorResponse(HttpServletRequest request, HttpServletResponse response, Exception e) throws IOException {
        StandardError standardError = new StandardError(System.currentTimeMillis(), HttpStatus.UNAUTHORIZED.value(), "Acesso não autorizado", e.getMessage(), request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(standardError));
    }
}
