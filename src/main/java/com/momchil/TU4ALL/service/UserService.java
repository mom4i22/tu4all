package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class UserService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserDBO readByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDBO readById(long id) {
        return userRepository.findById(id).get();
    }

    public void createUser(UserDBO userDBO) {
        userDBO.setPassword(bCryptPasswordEncoder.encode(userDBO.getPassword()));
        try {
            userRepository.save(userDBO);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void createUserWithPicture(UserDBO userDBO, MultipartFile image) {
        userDBO.setPassword(bCryptPasswordEncoder.encode(userDBO.getPassword()));
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        if(fileName.contains("..")) {
            logger.error("Not a valid file name");
        }
        try {
            userDBO.setProfilePicture(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
        try {
            userRepository.save(userDBO);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public UserDBO editUser(long id, UserDBO userDBO) {
        UserDBO user = userRepository.findById(id).get();
        user.setAlias(userDBO.getAlias());
        user.setEmail(userDBO.getEmail());
        user.setName(userDBO.getName());
        user.setFaculty(userDBO.getFaculty());
        user.setFacultyNumber(userDBO.getFacultyNumber());
        user.setDateOfBirth(userDBO.getDateOfBirth());
        user.setFriends(userDBO.getFriends());
        userRepository.save(user);
        return user;
    }

    public boolean deleteByUserId(long id) {
        try {
            UserDBO userDBO = userRepository.findById(id).get();
            userRepository.delete(userDBO);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public UserDBO readByAlias(String alias) {
        return userRepository.findByAlias(alias);
    }
}
