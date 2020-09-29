package com.webapp.webdemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/web/users")
public class UserController extends AbstractController{
    @GetMapping
    public ResponseEntity<String> test(){
        return ResponseEntity.ok("test");
    }
}
