package by.ilmizeroth.birthdaycalendar.service;

import by.ilmizeroth.birthdaycalendar.entity.UserEntity;
import by.ilmizeroth.birthdaycalendar.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.
                getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("Not authenticated");
        }
        String name =  auth.getName();
        return userRepository.findByName(name).
                orElseThrow(() -> new UsernameNotFoundException(name));
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }
}
