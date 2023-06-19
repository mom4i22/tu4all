package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.FriendshipDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.FriendshipRepository;
import com.momchil.TU4ALL.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private FriendshipRepository friendshipRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, FriendshipRepository friendshipRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.friendshipRepository = friendshipRepository;
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

    public void createUserWithPicture(String alias, String name, String email, String password, String dateOfBirth, String faculty, String facultyNumber, String role, MultipartFile profilePic) throws ParseException {
        UserDBO userDBO = new UserDBO();
        userDBO.setPassword(bCryptPasswordEncoder.encode(password));
        String fileName = StringUtils.cleanPath(profilePic.getOriginalFilename());
        if(fileName.contains("..")) {
            logger.error("Not a valid file name");
        }
        try {
            userDBO.setProfilePicture(Base64.getEncoder().encodeToString(profilePic.getBytes()));
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
        userDBO.setAlias(alias);
        userDBO.setName(name);
        userDBO.setEmail(email);
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        long millis = date.getTime();
        userDBO.setDateOfBirth(new java.sql.Date(millis));
        userDBO.setFaculty(faculty);
        userDBO.setFacultyNumber(facultyNumber);
        userDBO.setRole(role);
        try {
            userRepository.save(userDBO);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void editUserWithProfilePic(long id, String name, String dateOfBirth, String faculty, String facultyNumber, MultipartFile profilePic) throws ParseException {
        String fileName = StringUtils.cleanPath(profilePic.getOriginalFilename());
        if(fileName.contains("..")) {
            logger.error("Not a valid file name");
        }
        UserDBO user = userRepository.findById(id).get();
        user.setName(name);
        user.setFaculty(faculty);
        user.setFacultyNumber(facultyNumber);
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        long millis = date.getTime();
        user.setDateOfBirth(new java.sql.Date(millis));
        try {
            user.setProfilePicture(Base64.getEncoder().encodeToString(profilePic.getBytes()));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        try {
            userRepository.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public List<UserDBO> readPeople(long id) {
        if(id == 0) {
            logger.error("Id is not correct" + id);
        }
        return userRepository.findAllExceptActiveUser(id);
    }

    public UserDBO editUser(long id, UserDBO userDBO) {
        UserDBO user = userRepository.findById(id).get();
        user.setAlias(userDBO.getAlias());
        user.setEmail(userDBO.getEmail());
        user.setName(userDBO.getName());
        user.setFaculty(userDBO.getFaculty());
        user.setFacultyNumber(userDBO.getFacultyNumber());
        user.setDateOfBirth(userDBO.getDateOfBirth());
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
