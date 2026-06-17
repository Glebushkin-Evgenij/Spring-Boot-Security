package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.model.Role;
import web.model.User;
import web.service.RoleService;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "/admin")
    public String welcome() {
        return "redirect:/admin/all";
    }

    @GetMapping(value = "admin/all")
    public String allUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "allUsersPage";
    }

    @GetMapping(value = "admin/add")
    public String addUser(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "addUser";
    }

//    @PostMapping(value = "admin/add")
//    public String postAddUser(@ModelAttribute("user") User user,
//                              @RequestParam(required=false) String roleAdmin,
//                              @RequestParam(required=false) String roleVIP) {
//        Set<Role> roles = new HashSet<>();
//        roles.add(roleService.getRoleByName("ROLE_USER"));
//        if (roleAdmin != null && roleAdmin.equals("ROLE_ADMIN")) {
//            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
//        }
//        user.setRoles(roles);
//        userService.addUser(user);
//
//        return "redirect:/admin";
//    }
    @PostMapping(value = "admin/add")
    public String postAddUser(@ModelAttribute("user") User user,
                              @RequestParam String role) { // принимаем один параметр role
        Set<Role> roles = new HashSet<>();

        // Всегда добавляем USER (если это ваша логика)
        Role userRole = roleService.getRoleByName("ROLE_USER");
        if (userRole != null) {
            roles.add(userRole);
        }

        // Если выбрали ADMIN — добавляем и его
        if ("ROLE_ADMIN".equals(role)) {
            Role adminRole = roleService.getRoleByName("ROLE_ADMIN");
            if (adminRole != null) {
                roles.add(adminRole);
            }
        }

        user.setRoles(roles);
        userService.addUser(user);

        return "redirect:/admin";
    }


    @GetMapping(value = "admin/edit/{id}")
    public String editUser(ModelMap model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        Set<Role> roles = user.getRoles();
        for (Role role: roles) {
            if (role.equals(roleService.getRoleByName("ROLE_ADMIN"))) {
                model.addAttribute("roleAdmin", true);
            }
        }
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/admin/edit")
    public String postEditUser(@RequestParam Long id,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String email,
                               @RequestParam(required = false) String password,
                               @RequestParam String role) {

        User user = userService.getUserById(id);
        if (user == null) {
            // можно выбросить исключение или вернуть на страницу с ошибкой
            return "redirect:/admin/all";
        }

        // Обновляем поля
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        // Если пароль не пустой — обновляем
        if (password != null && !password.isBlank()) {
            user.setPassword(password); // в реальном проекте тут должно быть кодирование BCrypt
        }

        // Роли: по умолчанию всегда есть USER, при выборе ADMIN добавляем и его
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        if ("ROLE_ADMIN".equals(role)) {
            roles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        user.setRoles(roles);

        userService.editUser(user);
        return "redirect:/admin/all";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/all";
    }

}
