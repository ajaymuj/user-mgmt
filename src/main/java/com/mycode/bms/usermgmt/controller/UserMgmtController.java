package com.mycode.bms.usermgmt.controller;

import com.mycode.bms.usermgmt.model.RegistrationResponse;
import com.mycode.bms.usermgmt.model.UserModel;
import com.mycode.bms.usermgmt.service.UserMgmtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Data
@Slf4j
@RestController
@RequestMapping("/control")
public class UserMgmtController {

    private final UserMgmtService userMgmtService;

    @GetMapping("/{userName}")
    public ResponseEntity <UserModel> getUserByName(@PathVariable("userName") String userName) {
        UserModel user = userMgmtService.getUserByUsername(userName);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public ResponseEntity <List<UserModel>> getAllUsers() {
        List < UserModel > userList = userMgmtService.loadAllUsers();
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/create")
    public ResponseEntity<RegistrationResponse> createUser(@RequestBody @Validated UserModel userModel) {
        RegistrationResponse response = userMgmtService.createUser(userModel);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/remove/{username}")
    public ResponseEntity<?> removeUserByUserName(@PathVariable("username") String username) {
        userMgmtService.removeUserByUserName(username);
        return ResponseEntity.ok(username);
    }

}
