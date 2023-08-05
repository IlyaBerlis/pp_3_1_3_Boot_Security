package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRoleById(long id);
    Role getRoleByName(String name);
    List<Role> getAllRoles();
    void addRole(Role role);
    void removeRole(Long id);
}
