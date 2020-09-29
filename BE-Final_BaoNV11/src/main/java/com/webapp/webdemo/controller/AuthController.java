package com.webapp.webdemo.controller;

import com.webapp.webdemo.payload.request.LoginRequest;
import com.webapp.webdemo.payload.request.SignUpRequest;
import com.webapp.webdemo.payload.response.ApiResponse;
import com.webapp.webdemo.payload.response.TokenResponse;
import com.webapp.webdemo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/web/auth")
public class AuthController extends AbstractController{

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest){
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(signUpRequest.getUsername()).toUri();
        return ResponseEntity.created(location).body(userService.registerUser(signUpRequest));
    }
}

