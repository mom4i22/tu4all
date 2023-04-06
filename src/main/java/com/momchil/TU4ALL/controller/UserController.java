package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.HashMap;
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
    public ResponseEntity<?> createUser(@RequestParam Map<String, String> requestParams, @RequestParam("profilePic") MultipartFile profilePic) {
        String alias = requestParams.get("alias");
        String name = requestParams.get("name");
        String email = requestParams.get("email");
        String password = requestParams.get("password");
        String dateOfBirth = requestParams.get("dateOfBirth");
        String faculty = requestParams.get("faculty");
        String facultyNumber = requestParams.get("facultyNumber");
        String role = requestParams.get("role");
        try {
            userService.createUserWithPicture(alias, name, email, password, dateOfBirth, faculty, facultyNumber, role, profilePic);
        } catch (ParseException e) {
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok("User created successfully");
    }

//    @PostMapping(value = "/create-user")
//    public ResponseEntity<?> createUser(@RequestBody UserDBO userDBO) {
//        userService.createUser(userDBO);
//        return ResponseEntity.ok(userDBO);
//    }


    @PutMapping("/edit-user/{id}")
    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody UserDBO userDBO, @RequestParam("profilePic") MultipartFile profilePicture) {
        userDBO = userService.editUserWithProfilePic(id,userDBO,profilePicture);
        return ResponseEntity.ok(userDBO);
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable long id) {
        boolean deleted = false;
        deleted = userService.deleteByUserId(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("get-user-by-alias/{alias}")
    public ResponseEntity<UserDBO> getUserByAlias(@PathVariable String alias) {
        UserDBO userDBO = userService.readByAlias(alias);
        return ResponseEntity.ok(userDBO);
    }

    @PutMapping("/change-password/{id}")
    public ResponseEntity<?> changeUserPassword(@PathVariable long id, @RequestParam String password) {
        UserDBO userDBO = userService.readById(id);
        userDBO.setPassword(password);
        userService.createUser(userDBO);
        return ResponseEntity.ok("SUCCESS");
    }

}
