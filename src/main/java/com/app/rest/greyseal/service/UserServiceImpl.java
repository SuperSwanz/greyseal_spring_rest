package com.app.rest.greyseal.service;

import com.app.rest.greyseal.model.User;
import com.app.rest.greyseal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User create(final User user) {
        return userRepository.save(user);
    }

    @Override
    public Set<User> getAll() {
        return userRepository.findAll().stream().collect(Collectors.toSet());
    }

    @Override
    public User getByEmail(final String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Boolean delete(final User user) {
        userRepository.delete(user);
        return true;
    }
}
