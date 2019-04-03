package com.app.rest.greyseal.controller;

import com.app.rest.greyseal.dto.UserDTO;
import com.app.rest.greyseal.mapper.UserMapper;
import com.app.rest.greyseal.model.User;
import com.app.rest.greyseal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService userService;

    private UserMapper userMapper = UserMapper.mapper;

    @GetMapping("/all")
    public ResponseEntity<Set<UserDTO>> getAll() {
        final Set<User> users = userService.getAll();
        return new ResponseEntity<>(userMapper.toUserDTOSet(users), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserDTO> get(@RequestParam("email") final String email) {
        final User user = userService.getByEmail(email);
        return new ResponseEntity<>(userMapper.toUserDTO(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody final UserDTO userDTO) {
        final User user = userMapper.toUser(userDTO);
        final UserDTO newUserDTO = userMapper.toUserDTO(userService.create(user));
        return new ResponseEntity<>(newUserDTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody final UserDTO userDTO) {
        final User dbUser = userService.getByEmail(userDTO.getEmail());
        if (null == dbUser) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        dbUser.setFirstName(userDTO.getFirstName());
        dbUser.setMiddleName(userDTO.getMiddleName());
        dbUser.setLastName(userDTO.getLastName());
        final User user = userService.create(dbUser);
        return new ResponseEntity<>(userMapper.toUserDTO(user), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Boolean> delete(@RequestParam("email") final String email) {
        final User dbUser = userService.getByEmail(email);
        if (null == dbUser) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.delete(dbUser), HttpStatus.OK);
    }
}
