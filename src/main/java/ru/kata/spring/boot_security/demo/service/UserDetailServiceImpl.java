package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username +  " not found");
        }
        return user;
    }
}
