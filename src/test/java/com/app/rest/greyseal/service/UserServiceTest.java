package com.app.rest.greyseal.service;

import com.app.rest.greyseal.model.User;
import com.app.rest.greyseal.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.app.rest.greyseal.mockdataloader.MockUserDataLoader.getUsers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        //Returns a mock user
        final User user = getUsers().stream().findFirst().get();

        //User to create/save
        final User userToCreate = getUsers().stream().findFirst().get();
        userToCreate.setId(null);

        /**
         * Returns mock user when when(userRepository.save(user)) is invoked
         * User repository is also mocked
         */
        when(userRepository.save(any(User.class))).thenReturn(user);

        final User newUser = userServiceImpl.create(userToCreate);

        //assert to verify the object
        assertThat(newUser).isEqualTo(user);

        //assert to verify the email
        assertThat(newUser.getEmail()).isEqualTo(user.getEmail()).hasToString("Email");
    }

    @Test
    public void getAll() {
        //Returns a mock user
        final List<User> databaseUsers = getUsers().stream().collect(Collectors.toList());

        /**
         * Returns mock user when when(userRepository.findAll()) is invoked
         * User repository is also mocked
         */
        when(userRepository.findAll()).thenReturn(databaseUsers);

        final Set<User> userSet = userServiceImpl.getAll();

        //assert to verify the set/list size
        assertThat(userSet).hasSize(databaseUsers.size());

        //assert to verify if the first objects are equal
        assertThat(userSet).first().isEqualTo(databaseUsers.get(0));

        //assert to verify the isActive flag of the first object
        assertThat(userSet).first().extracting(User::getIsActive).isEqualTo(false);

        //assert to verify the isActive flag of the all objects
        assertThat(userSet).extracting(User::getIsActive).isEqualTo(Arrays.asList(false, false));
    }

    @Test
    public void getByEmail() {
        //Returns a mock user
        final User user = getUsers().stream().findFirst().get();

        /**
         * Returns mock user when when(userRepository.findUserByEmail(email)) is invoked
         * User repository is also mocked
         */
        when(userRepository.findUserByEmail(anyString())).thenReturn(user);

        final User newUser = userServiceImpl.getByEmail("Email");

        //assert to verify the object
        assertThat(newUser).isEqualTo(user);

        //assert to verify the email
        assertThat(newUser.getEmail()).isEqualTo(user.getEmail()).hasToString("Email");
    }

    @Test
    public void delete() {
        //Returns a mock user
        final User user = getUsers().stream().findFirst().get();

        /**
         * Mocking void delete method with Mockito
         *
         */
        doNothing().when(userRepository).delete(any(User.class));

        final Boolean isDeleted = userServiceImpl.delete(user);

        //assert to verify the object
        assertThat(isDeleted).isEqualTo(true);
    }
}
