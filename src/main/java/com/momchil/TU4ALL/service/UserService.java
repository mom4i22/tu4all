package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDBO findUserByAlias(String alias) {
        return userRepository.findByAlias(alias);
    }

    public void createUser(UserDBO userDBO) {
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
       UserDBO userDBO = userRepository.findByAlias(alias);
       return userDBO;
    }
}
