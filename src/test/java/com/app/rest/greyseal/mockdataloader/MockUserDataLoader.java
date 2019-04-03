package com.app.rest.greyseal.mockdataloader;

import com.app.rest.greyseal.dto.UserDTO;
import com.app.rest.greyseal.model.User;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MockUserDataLoader {

    public static Set<User> getUsers() {
        final Set<User> users = new HashSet<>(Math.max((int) (2 / .75f) + 1, 16));
        final User createdBy = new User();
        createdBy.setEmail("email@createdby.com");

        final Date dt = new Date(1553982808060l);
        User user = new User();
        user.setFirstName("FirstName");
        user.setMiddleName("MiddleName");
        user.setLastName("LastName");
        user.setIsActive(false);
        user.setPhoneNumber("PhoneNumber");
        user.setEmail("Email");
        user.setCreatedBy(createdBy);
        user.setUpdatedBy(createdBy);
        user.setId(1l);
        user.setCreatedDate(null);
        user.setUpdatedDate(null);
        user.setVersion(1);
        users.add(user);

        user = new User();
        user.setFirstName("Name First");
        user.setMiddleName("Name Middle");
        user.setLastName("Name Last");
        user.setIsActive(false);
        user.setPhoneNumber("Number Phone");
        user.setEmail("Email");
        user.setCreatedBy(createdBy);
        user.setUpdatedBy(createdBy);
        user.setId(2l);
        user.setCreatedDate(null);
        user.setUpdatedDate(null);
        user.setVersion(2);
        users.add(user);

        return users;
    }

    public static Set<UserDTO> getUsersDTO() {
        final Set<UserDTO> users = new HashSet<>(Math.max((int) (2 / .75f) + 1, 16));
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1l);
        userDTO.setFirstName("FirstName");
        userDTO.setLastName("LastName");
        userDTO.setPhoneNumber("PhoneNumber");
        userDTO.setEmail("Email");
        users.add(userDTO);

        userDTO = new UserDTO();
        userDTO.setId(2l);
        userDTO.setFirstName("Name First");
        userDTO.setLastName("Name Last");
        userDTO.setPhoneNumber("Number Phone");
        userDTO.setEmail("Email");
        users.add(userDTO);

        return users;
    }
}
