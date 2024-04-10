package com.api.pokemonreview.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public ResponseEntity<String> home() {
        String welcome = "Welcome to my Pokemon Review API";
        return new ResponseEntity<>(welcome, HttpStatus.OK);
    }
}
