package com.crud.api.controllers;

import com.crud.api.config.JwtUtils;
import com.crud.api.models.JwtToken;
import com.crud.api.models.User;
import com.crud.api.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<JwtToken> signUp(@Valid @RequestBody User user){
        String password = user.getPassword();
        authenticationService.signUp(user);      //registering user
        user.setPassword(password);

        return authenticate(user);             //generating token for user
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@RequestBody User user){
        return authenticate(user);
    }

    //method used for authenticating user and generating authentication token
    private ResponseEntity<JwtToken> authenticate(User user){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(new JwtToken("Bad Credentials"));
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new JwtToken(token));
    }
}
