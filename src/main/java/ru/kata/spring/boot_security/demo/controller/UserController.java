package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user/")
    public String userHome(Model model, Principal principal){
        String username = principal.getName();
        User user = userService.getUserByName(username);
        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/admin/")
    public String adminHome(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/admin/{id}")
    public String getUser(@PathVariable(value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/admin/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());

        return "create";
    }

    @PostMapping("/admin/new")
    public String add(@ModelAttribute("user") User user) {
        userService.addUser(user);

        return "redirect:/";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User updateUser){
        User user = userService.getUserById(id);

        if (user == null){
            return "redirect:/";
        }

        user.setName(updateUser.getName());
        user.setSurname(updateUser.getSurname());
        user.setAge(updateUser.getAge());
        user.setSex(updateUser.getSex());
        userService.updateUser(user);

        return "redirect:/";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);

        return "edit";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUser(id);

        return "redirect:/";
    }

    @GetMapping("admin/add-role/{userId}/{roleId}")
    public String addUserRole(@PathVariable("userId") long userId, @PathVariable("roleId") long roleId){
        User user = userService.getUserById(userId);
        Role role = roleService.getRoleById(roleId);

        if (user != null && role != null){
            user.addRole(role);
            userService.updateUser(user);
        }

        return "redirect:/admin/edit/{userId}";
    }

    @GetMapping("admin/remove-role/{userId}/{roleId}")
    public String removeUserRole(@PathVariable("userId") long userId, @PathVariable("roleId") long roleId){
        User user = userService.getUserById(userId);
        Role role = roleService.getRoleById(roleId);

        if (user != null && role != null){
            user.removeRole(role);
            userService.updateUser(user);
        }

        return "redirect:/admin/edit/{userId}";
    }
}