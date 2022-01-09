package com.mycode.bms.usermgmt.service;

import com.mycode.bms.usermgmt.config.JWTHelper;
import com.mycode.bms.usermgmt.entity.Role;
import com.mycode.bms.usermgmt.entity.User;
import com.mycode.bms.usermgmt.exception.DuplicateUsernameException;
import com.mycode.bms.usermgmt.exception.InvalidUserException;
import com.mycode.bms.usermgmt.exception.InvalidUserTypeException;
import com.mycode.bms.usermgmt.model.*;
import com.mycode.bms.usermgmt.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@Slf4j
@Service
public class UserMgmtService implements UserDetailsService {

    private final UserRepository userRepository;

//    public User createUser(User user) {
//        return userMgmtDAO.createUser(user);
//    }
//
//    public List<User> getUsers(String type) {
//        List<String> userTypeList = null;
//        if("all".equalsIgnoreCase(type)) {
//            userTypeList = userMgmtDAO.getDistinctUsers();
//        } else {
//            if(null==userTypeList)
//                userTypeList = new ArrayList<>();
//            userTypeList.add(type);
//        }
//        return userMgmtDAO.getUsers(userTypeList);
//    }
//
//    public User modifyUser(User user) {
//        return userMgmtDAO.modifyUser(user);
//    }
//
//    public void removeUser(String username) {
//        userMgmtDAO.removeUser(username);
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return new LoginUserDetail(user);
    }

    public UserModel getUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return getUserModel(user);
    }

    private UserModel getUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserName(user.getUserName());
        userModel.setFirstName(user.getFirstName());
        userModel.setLastName(user.getLastName());
        userModel.setUserType(user.getUserType());
        userModel.setEmailId(user.getEmail());
        userModel.setPhoneNo(user.getPhoneNo());
        userModel.setActive(user.getActive());
        return userModel;
    }

    public Boolean checkUserNameExists(String username) throws DuplicateUsernameException {
        final Optional < User > user = userRepository.findByUserName(username);
        if(user.isPresent()) {
            throw new DuplicateUsernameException(HttpStatus.BAD_REQUEST,
                    "Username already exists! please choose different username");
        }
        return true;
    }

    public RegistrationResponse handleUserRegistration(UserModel userModel) {
        final Boolean userNameExists = checkUserNameExists(userModel.getUserName());
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
        userRepository.save(user);
        RegistrationResponse response = new RegistrationResponse();
        response.setMessage(message);
        return response;
    }

    public UserDetails loadUserByUserId(Long userId) throws InvalidUserException {
        final User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(HttpStatus.NOT_FOUND, "User Not Found"));
        return new LoginUserDetail(user);
    }

    public List<User> loadUsersByType(String type) {
        final List<User> userList = userRepository.findByUserType(type)
                .orElseThrow(() -> new InvalidUserException(HttpStatus.NOT_FOUND, "User Not Found"));
        return userList;
    }

    public List< UserModel> loadAllUsers() {
        final List<User> userList = userRepository.findAll();
        List<UserModel> userModelList = new ArrayList <>();
        UserModel userModel = null;
        for(User user : userList) {
            userModel = getUserModel(user);
            userModelList.add(userModel);
        }
        return userModelList;
    }

    public RegistrationResponse createUser(UserModel userModel) {
        RegistrationResponse response = handleUserRegistration(userModel);
        return response;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void removeUserByUserName(String username) {
        userRepository.deleteByUserName(username);
    }

    public void removeUserByUserId(Long id) {
        userRepository.deleteById(id);
    }
}
