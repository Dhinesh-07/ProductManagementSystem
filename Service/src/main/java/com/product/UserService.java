package com.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);



        @Autowired
        private UserRepository userRepository;

        public UserDetails saveUser(UserDetails userDetails) {
            return userRepository.save(userDetails);
        }

    public UserDetails getUserByUsername(String username) {
        Optional<UserDetails> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null); // or handle the case where user is not found
    }
    public UserDetails getUserById(Integer id) {
        Optional<UserDetails> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null); // or handle the case where user is not found
    }
        public boolean login(String username, String password) {
            LOGGER.info("Checking user: {}", username);
            Optional<UserDetails> optionalUser = userRepository.findByUsernameAndPassword(username, password);
            return optionalUser.isPresent();
        }

    public List<UserDetails> getAllUser() {
        return userRepository.findAll();
    }





    public Optional<UserDetails> getUserWithRoleByUsername(String username) {
        // Implement logic to retrieve user with role by username from the database
        return userRepository.findByUsername(username);
    }
    public boolean authenticateUser(String username, String password) {
        // Implement logic to authenticate user based on username and password
        Optional<UserDetails> optionalUser = userRepository.findByUsername(username);
        return optionalUser.isPresent() && optionalUser.get().getPassword().equals(password);
    }



}

