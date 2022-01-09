package com.mycode.bms.usermgmt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "USER_NAME")
    @NonNull
    private String userName;

    @Column(name = "PASSWORD")
    @NonNull
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "STATUS")
    @NonNull
    private Boolean active;

    @Column(name = "USER_TYPE")
    private String userType;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "TIME_ZONE")
    private String timeZone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roleId", fetch = FetchType.EAGER)
    private List<Role> roles;

    @JsonIgnore
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonIgnore
    @Column(name = "MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    @JsonIgnore
    @Column(name = "LAST_LOGGED_IN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoggedIn;

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + active + '\'' +
                ", userType='" + userType + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", roles=" + roles +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", lastLoggedIn=" + lastLoggedIn +
                '}';
    }
}
