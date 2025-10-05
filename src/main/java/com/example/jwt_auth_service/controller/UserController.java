package com.example.jwt_auth_service.controller;

import com.example.jwt_auth_service.dto.*;
import com.example.jwt_auth_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @Operation(summary = "Get all users with pagination, sorting and filters")
    @GetMapping
    public ResponseEntity<PageResponse<UserDTO>> getAllUsers(
            @Parameter(description = "Page of Number", example = "1")
            @RequestParam(defaultValue = "1") int page
            , @Parameter(description = "Size", example = "10")
            @RequestParam(defaultValue = "10") int size
            , @Parameter(description = "sort field and direction", example = "id,asc")
            @RequestParam(defaultValue = "id,asc") String[] sort
            , @RequestParam(required = false) String status
            , @RequestParam(required = false) String contract
            , @RequestParam(required = false) String codeId
            , @RequestParam(required = false) String name
            , @RequestParam(required = false) String email
            , @RequestParam(required = false) String companyName
            , @RequestParam(required = false) String address
            , @RequestParam(required = false) String telephone
    ) {
        String sortBy = sort[0];
        String sortDir = sort.length > 1 ? sort[1] : "asc";
        var usersPage = userService.getAllUsers(page, size, sortBy, sortDir, status, contract, codeId, name, email, companyName, address, telephone);
        return ResponseEntity.ok(usersPage);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserCreateRequest req) {
        UserDTO saved = userService.createUser(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateUserPartial(@PathVariable Long id, @RequestBody java.util.Map<String, Object> updates) {
        UserDTO updated = userService.updateUserPartial(id, updates);
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

    @Operation (summary = "Get user by email")
    @PostMapping("/ByEmail")
    public ResponseEntity<?> getByEmail(@Valid @RequestBody UserEmailRequest obj) {
        try {
            UserDTO user = userService.getByEmail(obj.getEmail());
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
