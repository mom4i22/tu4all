package com.momchil.TU4ALL.controller;

import com.momchil.TU4ALL.dbo.UserDBO;
import com.momchil.TU4ALL.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserDBO userDBO) {
        userService.createUser(userDBO);
        return ResponseEntity.ok(userDBO);
    }

    @PutMapping("/edit-user/{id}")
    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody UserDBO userDBO) {
        userDBO = userService.editUser(id,userDBO);
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
