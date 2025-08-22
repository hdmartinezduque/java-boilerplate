package com.example.jwt_auth_service.controller;

import com.example.jwt_auth_service.dto.ApiResponse;
import com.example.jwt_auth_service.dto.PageResponse;
import com.example.jwt_auth_service.model.User;
import com.example.jwt_auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<PageResponse<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        PageResponse<User> users = userService.getAllUsers(page, size, sortBy, sortDir);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUserPartial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        String message = "";
        User updatedUser = userService.updateUserPartial(id, updates);
            if(userService.existsById(id)){
                message = "User with ID " + id + " does not exist.";
                System.out.println(message);
                return ResponseEntity.status(NOT_FOUND)
                        .body(new ApiResponse(HttpStatus.OK.value(), message)); // Return an empty User object or handle as needed
            } else {
                message = "User with ID " + id + " updated successfully.";
                ApiResponse response = new ApiResponse(HttpStatus.OK.value(), message);
                System.out.println(message);
                return ResponseEntity.ok(response);
            }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        String message = "";
        if (userService.existsById(id)) {
            message = "User with ID " + id + " does not exist.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), message));
        }else {
            userService.deleteUser(id);
            message = "User with ID " + id + " deleted successfully.";
            ApiResponse response = new ApiResponse(HttpStatus.OK.value(), message);
            return ResponseEntity.ok(response);
        }
    }
}