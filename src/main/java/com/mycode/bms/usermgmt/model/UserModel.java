package com.mycode.bms.usermgmt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @NonNull
    private String userName;
    @NonNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String userType;
    @NonNull
    private String emailId;
    @NonNull
    private String phoneNo;
    @NonNull
    private Boolean active;

}
