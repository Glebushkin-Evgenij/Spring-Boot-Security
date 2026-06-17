package web.repository;

import web.model.User;

import java.util.List;

public interface UserRepo {
    void addUser(User user);
    void deleteUser(Long id);
    void editUser(User user);
    List<User> getAllUsers();
    User getUserByUsername(String username);
    User getUserById(Long id);



}
