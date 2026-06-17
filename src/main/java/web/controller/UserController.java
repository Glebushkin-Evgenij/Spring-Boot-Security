package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


//    @GetMapping(value = "/lk")
//    public String getUserPage2(ModelMap modelMap, Principal principal) {
//        modelMap.addAttribute("user", userService.loadUserByUsername(principal.getName()));
//        return "userPage";
//    }

    @GetMapping("/lk")
    public String getUserPage(ModelMap modelMap, Principal principal) {
        // Получаем текущего пользователя по имени из Principal
        User user = (User) userService.loadUserByUsername(principal.getName());

        // Кладем в модель список из одного пользователя под именем "users"
        // Это нужно, чтобы в Thymeleaf работало th:each="user : \${users}"
        modelMap.addAttribute("users", java.util.Collections.singletonList(user));

        return "userPage";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getUserById(id));
        return "userPage";
    }
}
