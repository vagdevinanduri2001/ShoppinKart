package com.cognizant.userManagement.service;

import com.cognizant.userManagement.dao.UserDao;
import com.cognizant.userManagement.entity.*;
import com.cognizant.userManagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();

        if(user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserId(), user.getPassword(), getAuthorities(user));
        }else {
            throw new UsernameNotFoundException("Username not found.");
        }
    }

    public Set getAuthorities(User user) {
        Set authorities = new HashSet();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });

        return authorities;
    }

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String userName = jwtRequest.getUserName();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(userName, userPassword);

        final UserDetails userDetails =  loadUserByUsername(userName);

        String newGeneratedToken = jwtUtil.generateToken(userDetails);
        User user = userDao.findById(userName).get();

        return new JwtResponse(user,newGeneratedToken);

    }

    public AuthenticationResponse validate(String token){
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        String jwt = token.substring(7);
        if(jwtUtil.validateToken(jwt)){
            authenticationResponse.setUserId(jwtUtil.getUserNameFromToken(jwt));
            authenticationResponse.setValid(true);
            authenticationResponse.setName(userDao.findById(jwtUtil.getUserNameFromToken(jwt.substring(7))).get().getUserId());
        }else {
            authenticationResponse.setValid(false);
        }
        return authenticationResponse;
    }

    public Set<Role> getRole(String userId){
        return userDao.findById(userId).get().getRole();

    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("User is disabled.");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credentials from user.");
        }
    }

}
