package com.basicproject.api.user;

import com.basicproject.api.email.EmailService;
import com.basicproject.api.user.userdto.CreateUser;
import com.basicproject.api.user.userdto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    EmailService _emailService;
    UserRepository _userRepository;

    public UserService(EmailService emailService,UserRepository userRepository){
        _emailService = emailService;
        _userRepository = userRepository;
    }
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    public User save(CreateUser userDto){
        User user = new User();
        if(userDto.getUserName()==null || userDto.getEmail()==null || userDto.getPassword()==null) return null;
        user.setUserName(userDto.getUserName());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        String activationToken = UUID.randomUUID().toString();
        user.setActivationToken(activationToken);
        _emailService.sendActivationEmail(user.getEmail(),user.getActivationToken());
        return _userRepository.save(user);
    }
    public List<UserResponse> getAllUsers(int id){
        List<User> usersInDb = _userRepository.findAll();
        List<UserResponse> convertedList = new ArrayList<>();
        for(var item: usersInDb){
            convertedList.add(new UserResponse(item));
        }
        List<UserResponse> filteredList = new ArrayList<>();
        for(var item : convertedList){
            if(item.getUserId()!=id)
            {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    public Boolean activate(String activationToken){
        User inDb = _userRepository.findByActivationToken(activationToken);
        if(inDb==null){
            return false;
        }
        inDb.setActive(true);
        inDb.setActivationToken(null);
        _userRepository.save(inDb);
        return true;
    }

    public User findByEmail(String email) {
        return _userRepository.findByEmail(email);
    }

    public boolean deleteUser(int id) {
        User inDb = _userRepository.findById(id);

        if(inDb==null){
            return false;
        }
        _userRepository.delete(inDb);

        return true;
    }

    public User getUser(int id){
        User inDb = _userRepository.findById(id);
        if(inDb== null) {
            return null;
        }
        return inDb;
    }
}
