package com.app.rest.greyseal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "user", indexes = {@Index(name = "INDX_USER_EMAIL", columnList = "email", unique = true),
        @Index(name = "INDX_USER_PHONENUMBER", columnList = "phone_number", unique = true)})
@Getter
@Setter
public class User extends Base {

    private static final long serialVersionUID = 2163974372669384885L;

    @NotEmpty
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @NotEmpty
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Email
    @NotEmpty
    @Column(name = "email", nullable = false, length = 20, unique = true)
    private String email;
}