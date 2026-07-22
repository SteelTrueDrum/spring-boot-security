package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // GET /admin - список всех пользователей
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    // GET /admin/new - форма создания пользователя
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-form";
    }

    // GET /admin/{id} - форма редактирования пользователя
    @GetMapping("/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-form";
    }

    // POST /admin - сохранение пользователя
    @PostMapping("/new")
    public String saveUser(@ModelAttribute("user") User user,
                           @RequestParam(value = "roleIds", required = false) Set<Long> roleIds) {

        userService.saveUser(user, roleIds);
        return "redirect:/admin";
    }

    // PUT /admin/{id} - обновление пользователя
    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam(value = "roleIds", required = false) Set<Long> roleIds) {
        user.setId(id);
        userService.updateUser(user, roleIds);
        return "redirect:/admin";
    }

    // DELETE /admin/{id} - удаление пользователя
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
