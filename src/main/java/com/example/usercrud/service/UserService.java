package com.example.usercrud.service;

import com.example.usercrud.dto.UserRequestDto;
import com.example.usercrud.exception.UserNotFound;
import com.example.usercrud.model.User;
import com.example.usercrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(UserRequestDto requestDto) {
        User user = new User();
        user.setName(requestDto.getName());
        user.setEmail((requestDto.getEmail()));
        user.setPassword(requestDto.getPassword());
        user.setContactNumber(requestDto.getContactNumber());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserNotFound {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("User not found with ID: " + id));
    }

    public User updateUser(Long id, UserRequestDto updatedUser) throws UserNotFound {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFound("Cannot update. User not found with ID: " + id));
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) throws UserNotFound {
        if (!userRepository.existsById(id)) {
            throw new UserNotFound("Cannot delete. User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}



