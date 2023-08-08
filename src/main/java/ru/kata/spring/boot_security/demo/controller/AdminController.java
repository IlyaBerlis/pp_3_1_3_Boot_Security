package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String adminHome(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable(value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());

        return "create";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("user") User user) {
        userService.addUser(user);

        return "redirect:/";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") long id, @ModelAttribute("user") User updateUser){
        User user = userService.getUserById(id);

        if (user == null){
            return "redirect:/";
        }

        user.updateFields(updateUser);
        userService.updateUser(user);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") long id, Model model) {
        User user = userService.getUserById(id);

        if (user == null) {
            return "redirect:/";
        }

        model.addAttribute("user", user);

        return "edit";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.removeUser(id);

        return "redirect:/";
    }

    @GetMapping("/add-role/{userId}/{roleId}")
    public String addUserRole(@PathVariable("userId") long userId, @PathVariable("roleId") long roleId){
        User user = userService.getUserById(userId);
        Role role = roleService.getRoleById(roleId);

        if (user != null && role != null){
            user.addRole(role);
            userService.updateUser(user);
        }

        return "redirect:/admin/edit/{userId}";
    }

    @GetMapping("/remove-role/{userId}/{roleId}")
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
