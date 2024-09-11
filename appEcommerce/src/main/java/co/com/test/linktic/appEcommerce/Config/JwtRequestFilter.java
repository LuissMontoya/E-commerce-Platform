package co.com.test.linktic.appEcommerce.Config;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import co.com.test.linktic.appEcommerce.entity.Users;
import co.com.test.linktic.appEcommerce.service.impl.UsersServiceImpl;
import co.com.test.linktic.appEcommerce.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UsersServiceImpl userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");

		String email = null;
		String jwt = null;
	
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwt = authorizationHeader.substring(7);
			email = jwtTokenUtil.getClaimsFromToken(jwt).getSubject();
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			if (jwtTokenUtil.isTokenValid(jwt)) {
		        Users userDetails = userRepository.findByEmail(email);

		        UsernamePasswordAuthenticationToken authenticationToken = 
		            new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());

		        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		}

		filterChain.doFilter(request, response);
	}

}
