package com.example.jwt_auth_service.controller;

import com.example.jwt_auth_service.dto.ApiResponse;
import com.example.jwt_auth_service.dto.PageResponse;
import com.example.jwt_auth_service.dto.UserCreateRequest;
import com.example.jwt_auth_service.model.User;
import com.example.jwt_auth_service.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

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
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        String sortBy = sort[0];
        String sortDir = sort.length > 1 ? sort[1] : "asc";

        PageResponse<User> usersPage = userService.getAllUsers(page, size, sortBy, sortDir);
        return ResponseEntity.ok(usersPage);
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody UserCreateRequest req) {
        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());
        User saved = userService.createUser(user, req.getCompanyId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserPartial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        //updates.keySet().retainAll(Set.of("name", "email", "password", "companyId"));
        User updated = userService.updateUserPartial(id, updates);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        if (!userService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(HttpStatus.NOT_FOUND.value(), "User with ID " + id + " not found."));
        }
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.value(), "User with ID " + id + " deleted successfully."));
    }
}
