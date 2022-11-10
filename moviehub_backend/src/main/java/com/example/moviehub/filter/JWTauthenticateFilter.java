package com.example.moviehub.filter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.moviehub.collection.JWTSubject;
import com.example.moviehub.service.Impl.UserServiceImpl;
import com.example.moviehub.util.JWTtokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class JWTauthenticateFilter extends OncePerRequestFilter {

    @Autowired
    private UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        final String authorizationHeader = request.getHeader("Authorization");
        System.out.println(authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String jwtToken = authorizationHeader.substring(7);
            System.out.println(jwtToken);
            try{
                JWTtokenUtil.validateToken(jwtToken);

                JWTSubject subject = JWTtokenUtil.decode(jwtToken);
                String userEmail = subject.getEmail();

                if (subject.isRefresh()){
                    if (!request.getServletPath().equals("/user/refresh")){
                        System.out.println("This is not an access token");
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "This is not an access token");
                        response.setContentType("text/plain");
                        return;
                    }
                }

                if (SecurityContextHolder.getContext().getAuthentication() == null){
                    //load userDetail with the email
                    UserDetails userDetails = this.userService.loadUserByUsername(userEmail);
                    logger.info( "token_info\n"+ "email: " + userDetails.getUsername() + "\n" + "pwd: " + userDetails.getPassword());


                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    filterChain.doFilter(request, response);
                }

            }catch (TokenExpiredException e){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setHeader("error", e.getMessage());
            }
//            catch (IllegalArgumentException e) {
//                System.out.println("Unable to get JWT Token");
//            } catch (Exception e) {
//                logger.warn(e.getMessage());
//            }
        } else {
            logger.warn("JWT Token not exist or does not begin with Bearer String");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }



    }
}
