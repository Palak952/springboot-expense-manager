package com.example.usercrud.controller;

import com.example.usercrud.Response.ApiResponse;
import com.example.usercrud.dto.UserRequestDto;
import com.example.usercrud.dto.UserResponseDto;
import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.User;
import com.example.usercrud.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto requestDto) {
        logger.info("Received request to create user: {}", requestDto.getName());
        User createdUser = userService.addUser(requestDto);
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(createdUser.getId());
        responseDto.setName(createdUser.getName());
        responseDto.setEmail(createdUser.getEmail());
        responseDto.setContactNumber(createdUser.getContactNumber());
        responseDto.setCreatedAt(createdUser.getCreatedAt());
        responseDto.setUpdatedAt(createdUser.getUpdatedAt());

        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "User created successfully", responseDto), HttpStatus.CREATED);

    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {
        logger.info("Received request to fetch all users");
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> responseDtos = new ArrayList<>();
        for (User user : users) {
            UserResponseDto dto = new UserResponseDto();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setContactNumber(user.getContactNumber());
            dto.setCreatedAt(user.getCreatedAt());
            dto.setUpdatedAt(user.getUpdatedAt());
            responseDtos.add(dto);

        }
        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "Users fetched successfully", responseDtos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Long id) throws UserNotFound {
        logger.info("Received request to fetch user with ID: {}", id);
        User user = userService.getUserById(id);
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setContactNumber(user.getContactNumber());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "User fetched successfully", dto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDto requestDto) throws UserNotFound {
        logger.info("Received request to update user with ID: {}", id);
        User updatedUser = userService.updateUser(id, requestDto);
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(updatedUser.getId());
        responseDto.setName(updatedUser.getName());
        responseDto.setEmail(updatedUser.getEmail());
        responseDto.setContactNumber(updatedUser.getContactNumber());
        responseDto.setCreatedAt(updatedUser.getCreatedAt());
        responseDto.setUpdatedAt(updatedUser.getUpdatedAt());

        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "User updated successfully", responseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long id) throws UserNotFound {
        logger.info("Received request to delete user with ID: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse<>("SUCCESS", "User deleted successfully", null), HttpStatus.OK);
    }
}








