package com.mycode.bms.usermgmt.repository;

import com.mycode.bms.usermgmt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor < User > {

    Optional<User> findByUserName(String userName);

    Optional< List <User> > findByUserType(String type);

    void deleteByUserName(String username);

//    @Query(value = "SELECT DISTINCT(USER_TYPE) FROM USER", nativeQuery = true)
//    public List<Object[]> findDistinctUserType();
//
//    @Query(value = "SELECT * FROM USER WHERE USER_TYPE IN (:userTypeList)")
//    public List<User> findUserByUserTypeList(@Param("userTypeList") List<String> userTypeList);
}
