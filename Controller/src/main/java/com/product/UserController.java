package com.product;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/adduser")
    public ResponseEntity<?> addUser(@RequestBody UserDetails userDetails) {
        try {
            logger.info("Received request to add user: {}", userDetails);
            UserDetails savedUser = userService.saveUser(userDetails);
            logger.info("User added successfully: {}", savedUser);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            logger.error("Failed to add user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDetails user) {
        try {
            UserDetails foundUser = userService.getUserByUsername(user.getUsername());

            if (foundUser != null) {
                boolean isAuthenticated = userService.authenticateUser(foundUser.getUsername(), user.getPassword());

                if (isAuthenticated) {
                    String userRole = foundUser.getRole();
                    logger.info("userRole"+userRole);
                    return ResponseEntity.status(HttpStatus.ACCEPTED)
                            .body(userRole);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body("Invalid credentials");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found");
            }
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAlUsers() {

        try {
            logger.info("Received request to fetch all categories.");
            List<UserDetails> User = userService.getAllUser();
            return ResponseEntity.ok(User);
        } catch (Exception e) {
            logger.error("Error occurred while fetching all categories: {}", e.getMessage());
            e.printStackTrace(); // For debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDetails> getUserByUsername(@PathVariable int  id) {
        try {
            logger.info("Received request to fetch user by username: {}", id);
            UserDetails user = userService.getUserById(id);
            logger.info("User fetched successfully: {}", user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error occurred while getting user by username: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
