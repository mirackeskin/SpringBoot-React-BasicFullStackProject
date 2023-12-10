package com.basicproject.api.user;

import com.basicproject.api.email.EmailService;
import com.basicproject.api.user.userdto.CreateUser;
import com.basicproject.api.user.userdto.ImageDto;
import com.basicproject.api.user.userdto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserService {

    EmailService _emailService;
    UserRepository _userRepository;

    public UserService(EmailService emailService,UserRepository userRepository){
        _emailService = emailService;
        _userRepository = userRepository;
    }
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    public boolean save(CreateUser userDto){
        User user = new User();
        if(userDto.getUserName()==null || userDto.getEmail()==null || userDto.getPassword()==null) return false;
        user.setUserName(userDto.getUserName());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        String activationToken = UUID.randomUUID().toString();
        user.setActivationToken(activationToken);
        _emailService.sendActivationEmail(user.getEmail(),user.getActivationToken());
        _userRepository.save(user);

        Path path = Paths.get("uploads","images",userDto.getUserName());
        File dir = new File(path.toUri());
        dir.mkdir();

        return true;
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

    public String profileImageUpload(int id , String imageContent){
        User inDb = _userRepository.findById(id);
        if( inDb== null){
            return null;
        }
        //inDb.setImage(imageContent);
        Date date = new Date();
        String createdFileName = ""+date.getTime()+".png";
        inDb.setImage(createdFileName);

        try {
            Path path = Paths.get("uploads","images",inDb.getUserName(), createdFileName);
            OutputStream outputStream = new FileOutputStream(path.toFile());
            byte [] base64decoded = Base64.getDecoder().decode(imageContent);
            outputStream.write(base64decoded);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return _userRepository.save(inDb).getImage();
    }
}
