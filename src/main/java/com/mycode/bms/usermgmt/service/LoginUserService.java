package com.mycode.bms.usermgmt.service;

import com.mycode.bms.usermgmt.config.JWTHelper;
import com.mycode.bms.usermgmt.entity.Role;
import com.mycode.bms.usermgmt.entity.User;
import com.mycode.bms.usermgmt.exception.InvalidUserTypeException;
import com.mycode.bms.usermgmt.model.AuthRequest;
import com.mycode.bms.usermgmt.model.AuthResponse;
import com.mycode.bms.usermgmt.model.RegistrationResponse;
import com.mycode.bms.usermgmt.model.UserModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@Slf4j
@Service
public class LoginUserService {

    private final JWTHelper jwtHelper;

    private final UserMgmtService userMgmtService;

    public AuthResponse authenticate(AuthRequest authRequest) {
        final UserDetails userDetails = userMgmtService.loadUserByUsername(authRequest.getUserName());
        final String jwtToken = jwtHelper.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(jwtToken);
        return authResponse;
    }

    public RegistrationResponse handleUserRegistration(UserModel userModel) {
        final Boolean userNameExists = userMgmtService.checkUserNameExists(userModel.getUserName());
        String message = "user registered successfully!";
        Calendar calendar = new GregorianCalendar();
        String timeZone = calendar.getTimeZone().getDisplayName();
        Date currentDate = new Date(System.currentTimeMillis());
        List < Role > roleList = new ArrayList <>();
        Role role = new Role();
        if("admin".equalsIgnoreCase(userModel.getUserType())) {
            role.setRoleName("admin");
            role.setDescription("Role provides access to all services!");
        } else if("owner".equalsIgnoreCase(userModel.getUserType())) {
            role.setRoleName("owner");
            role.setDescription("Role provides access to manage theatres, screens and shows!");
        } else if("user".equalsIgnoreCase(userModel.getUserType())) {
            role.setRoleName("user");
            role.setDescription("Role provides access to booking tickets!");
        } else {
            throw new InvalidUserTypeException(HttpStatus.NOT_ACCEPTABLE,
                    "Invalid User type! Enter correct usertype.");
        }
        roleList.add(role);
        User user = new User();
        user.setUserName(userModel.getUserName());
        user.setPassword(userModel.getPassword());
        user.setFirstName(userModel.getFirstName());
        user.setLastName(userModel.getLastName());
        user.setActive(true);
        user.setUserType(userModel.getUserType());
        user.setEmail(userModel.getEmailId());
        user.setPhoneNo(userModel.getPhoneNo());
        user.setCreatedDate(currentDate);
        user.setModifiedDate(currentDate);
        user.setLastLoggedIn(currentDate);
        user.setRoles(roleList);
        userMgmtService.createUser(user);
        RegistrationResponse response = new RegistrationResponse();
        response.setMessage(message);
        return response;
    }

    public UserModel getUserByUsername(String userName) {
        return userMgmtService.getUserByUsername(userName);
    }
}
