package com.momchil.TU4ALL.service;

import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.repository.CourseRepository;
import com.momchil.TU4ALL.repository.FriendshipRepository;
import com.momchil.TU4ALL.repository.UserRepository;
import com.momchil.TU4ALL.utils.Roles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private FriendshipRepository friendshipRepository;
    @Mock
    private CourseRepository courseRepository;
    private AutoCloseable autoCloseable;
    private UserService userService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, bCryptPasswordEncoder, friendshipRepository, courseRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void readAllWithoutCourses() {
        //when
        userService.readAllWithoutCourses();
        //then
        verify(userRepository).findAll();
    }

    @Test
    void readByEmail() {
    }

    @Test
    void readById() {
    }

    @Test
    void createUserWithoutPicture() throws ParseException {
        //given
        UserDBO userDBO = new UserDBO();
        String alias = "alias";
        userDBO.setAlias(alias);

        String name = "name";
        userDBO.setName(name);

        String faculty = "EEE";
        userDBO.setFaculty(faculty);

        String password = "password";
        userDBO.setPassword(password);

        String dateOfBirth = "2000-03-25";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
        long millis = date.getTime();
        userDBO.setDateOfBirth(new java.sql.Date(millis));

        String email = "email@email.com";
        userDBO.setEmail(email);

        String facultyNumber = "1235456";
        userDBO.setFacultyNumber(facultyNumber);

        String role = Roles.ROLE_USER.getValue();
        userDBO.setRole(role);

        //when
        userService.createUserWithoutPicture(userDBO);

        //then
        ArgumentCaptor<UserDBO> userDBOArgumentCaptor = ArgumentCaptor.forClass(UserDBO.class);
        verify(userRepository).save(userDBOArgumentCaptor.capture());

        UserDBO capturedUserDBO = userDBOArgumentCaptor.getValue();
        assertThat(capturedUserDBO).isEqualTo(userDBO);
    }

    @Test
    void deleteByUserId() {
    }

    @Test
    void readByAlias() {
    }
}