package com.app.rest.greyseal.service;

        import com.app.rest.greyseal.model.User;

        import java.util.Set;

public interface UserService {
    public User create(final User user);

    public Set<User> getAll();

    public User getByEmail(final String email);

    public Boolean delete(final User user);
}
