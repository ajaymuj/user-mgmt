package com.mycode.bms.usermgmt.controller;

import com.mycode.bms.usermgmt.config.JWTHelper;
import com.mycode.bms.usermgmt.model.*;
import com.mycode.bms.usermgmt.service.LoginUserService;
import com.mycode.bms.usermgmt.service.UserMgmtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Data
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginUserService loginUserService;

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody UserModel userModel) {
        RegistrationResponse response = loginUserService.handleUserRegistration(userModel);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = loginUserService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

}
