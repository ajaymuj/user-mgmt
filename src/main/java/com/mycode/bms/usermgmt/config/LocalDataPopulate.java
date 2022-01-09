package com.mycode.bms.usermgmt.config;

import com.mycode.bms.usermgmt.entity.Role;
import com.mycode.bms.usermgmt.entity.User;
import com.mycode.bms.usermgmt.repository.UserRepository;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Component
//@Profile("local")
public class LocalDataPopulate implements CommandLineRunner {

    Logger logger = LoggerFactory.getLogger(LocalDataPopulate.class);

//    @Autowired
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private List<Role> adminRoles = new ArrayList<>();
    private List<Role> userRoles = new ArrayList<>();

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();

        Role adminRole = new Role("admin","can access all services");
        adminRoles.add(adminRole);
        User adminUser = User.builder()
                .userName("ajaymuj")
                .password(passwordEncoder.encode("super@123"))
                .firstName("Ajay")
                .lastName("Mujumdar")
                .userType("admin")
                .createdDate(new Date(System.currentTimeMillis()))
                .email("ajaymuj@gmail.com")
                .phoneNo("9972014499")
                .lastLoggedIn(new Date(System.currentTimeMillis()))
                .timeZone("GMT+5.30")
                .modifiedDate(new Date(System.currentTimeMillis()))
                .roles(adminRoles)
                .active(true)
                .build();

        Role theatreRole = new Role("owner","can access all services");
        adminRoles.add(theatreRole);
        User theatre = User.builder()
                .userName("ajaymuj")
                .password(passwordEncoder.encode("super@123"))
                .firstName("Ajay")
                .lastName("Mujumdar")
                .userType("admin")
                .createdDate(new Date(System.currentTimeMillis()))
                .email("ajaymuj@gmail.com")
                .phoneNo("9972014499")
                .lastLoggedIn(new Date(System.currentTimeMillis()))
                .timeZone("GMT+5.30")
                .modifiedDate(new Date(System.currentTimeMillis()))
                .roles(adminRoles)
                .active(true)
                .build();

        Role userRole = new Role("user","has restricted access");
        userRoles.add(userRole);
        User normalUser = User.builder()
                .userName("user")
                .password(passwordEncoder.encode("pass"))
                .firstName("User")
                .lastName("Parent")
                .userType("user")
                .createdDate(new Date(System.currentTimeMillis()))
                .email("abc@gmail.com")
                .phoneNo("9123456789")
                .lastLoggedIn(new Date(System.currentTimeMillis()))
                .timeZone("GMT")
                .modifiedDate(new Date(System.currentTimeMillis()))
                .roles(userRoles)
                .active(true)
                .build();

        Role userRoleOld = new Role("user","has restricted access");
        userRoles.add(userRole);
        User inactiveUser = User.builder()
                .userName("iauser")
                .password(passwordEncoder.encode("pass"))
                .firstName("Iauser")
                .lastName("Father")
                .userType("user")
                .createdDate(new Date(System.currentTimeMillis()))
                .email("iabc@gmail.com")
                .phoneNo("9876543219")
                .lastLoggedIn(new Date(System.currentTimeMillis()))
                .timeZone("CST")
                .modifiedDate(new Date(System.currentTimeMillis()))
                .roles(userRoles)
                .active(false)
                .build();

        logger.info("save all users!");
        userRepository.saveAll(Arrays.asList(adminUser, normalUser, inactiveUser));
//        logger.info("saving admin user!");
//        userMgmtDAO.save(adminUser);
//        logger.info("saving normal user!");
//        userMgmtDAO.save(normalUser);
    }

}
