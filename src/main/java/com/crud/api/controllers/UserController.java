package com.crud.api.controllers;

import com.crud.api.models.User;
import com.crud.api.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/get/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username){
        try {
            User user = customUserDetailsService.getUser(username);
            user.setPassword("");
            return ResponseEntity.ok(user);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updatedUser = customUserDetailsService.updateUser(user);
        if (updatedUser!=null){
            updatedUser.setPassword("");
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.internalServerError().body(null);
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<User> deleteUser(@PathVariable String username){
        User user = customUserDetailsService.deleteUser(username);
        if (user!=null){
            user.setPassword("");
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.internalServerError().body(null);
    }
}
