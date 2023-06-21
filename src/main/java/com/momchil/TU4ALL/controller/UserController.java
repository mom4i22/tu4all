package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.CourseDBO;
import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.service.UserService;
import com.momchil.TU4ALL.utils.Roles;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UserController {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/create-user", headers = "content-type=multipart/*")
    public ResponseEntity<?> createUser(@RequestParam("alias") String alias, @RequestParam("name") String name, @RequestParam String email,
                                        @RequestParam("password") String password, @RequestParam("dateOfBirth") String dateOfBirth,
                                        @RequestParam("faculty") String faculty, @RequestParam String facultyNumber, @RequestParam("profilePic") MultipartFile profilePic) {
        try {
            if(email.contains("teacher")){
                userService.createUserWithPicture(alias, name, email, password, dateOfBirth, faculty, facultyNumber, Roles.ROLE_ADMIN.getValue(), profilePic);
            }
            else{
                userService.createUserWithPicture(alias, name, email, password, dateOfBirth, faculty, facultyNumber, Roles.ROLE_USER.getValue(), profilePic);
            }
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok("User created successfully");
    }

    @PutMapping("/edit-user/{id}")
    public ResponseEntity<?> editUser(@PathVariable long id, @RequestParam("name") String name, @RequestParam("dateOfBirth") String dateOfBirth,
                                      @RequestParam("faculty") String faculty, @RequestParam String facultyNumber, @RequestParam("profilePic") MultipartFile profilePicture) throws ParseException {
        userService.editUserWithProfilePic(id, name, dateOfBirth, faculty, facultyNumber, profilePicture);
        return ResponseEntity.ok("Edit user successfully");
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable long id) {
        boolean deleted = false;
        deleted = userService.deleteByUserId(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-user-by-alias/{alias}")
    public ResponseEntity<UserDBO> getUserByAlias(@PathVariable String alias) {
        UserDBO userDBO = userService.readByAlias(alias);
        return ResponseEntity.ok(userDBO);
    }

    @PutMapping("/change-password/{email}")
    public ResponseEntity<?> changeUserPassword(@PathVariable String email, @RequestParam("password") String password) {
        UserDBO userDBO = userService.readByEmail(email);
        userDBO.setPassword(password);
        userService.createUser(userDBO);
        return ResponseEntity.ok("Password successfully changed!");
    }

    @GetMapping("/get-people/{id}")
    public ResponseEntity<List<UserDBO>> getPeople(@PathVariable long id) {
        List<UserDBO> users = userService.readPeople(id);
        if(users.size() == 0) {
            logger.info("[getPeople] No other users found");
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/get-all-users/{id}")
    public ResponseEntity<List<UserDBO>> getAllUsers(@PathVariable long id, @RequestParam("courseId") String courseId) {
        List<UserDBO> userDBOS = userService.readAll(id, Long.parseLong(courseId));
        return ResponseEntity.ok(userDBOS);
    }

    @GetMapping("/get-all-courses-for-user/{id}")
    public ResponseEntity<List<CourseDBO>> getAllCoursesForStudent(@PathVariable long id) {
        List<CourseDBO> courseDBOS = userService.readAllCoursesForUser(id);
        return ResponseEntity.ok(courseDBOS);
    }

}
