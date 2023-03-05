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

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDBO userDBO) {
        userService.createUser(userDBO);
        return ResponseEntity.ok("SUCCESS");
    }

    @PutMapping("/editUser/{id}")
    public ResponseEntity<?> editUser(@PathVariable long id, @RequestBody UserDBO userDBO) {
        userDBO = userService.editUser(id,userDBO);
        return ResponseEntity.ok(userDBO);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Map<String,Boolean>> deleteUser(@PathVariable long id) {
        boolean deleted = false;
        deleted = userService.deleteByUserId(id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted",deleted);
        return ResponseEntity.ok(response);
    }

    @GetMapping("getUserByAlias/{alias}")
    public ResponseEntity<UserDBO> getUserByAlias(@PathVariable String alias) {
        UserDBO userDBO = userService.readByAlias(alias);
        return ResponseEntity.ok(userDBO);
    }
}
