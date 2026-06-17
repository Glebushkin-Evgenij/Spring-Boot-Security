package web.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {


    User getUserById(Long id);
    List<User> getAllUsers();
    void addUser(User user);
    void deleteUser(Long id);
    void editUser(User user);
}
