package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void removeUser(long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserByName(String username) {
        return userRepository.findByName(username);
    }
}