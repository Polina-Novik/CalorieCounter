package by.novik.caloriecounter.service;

import by.novik.caloriecounter.dto.AuthRequest;
import by.novik.caloriecounter.dto.ProfileResponse;
import by.novik.caloriecounter.dto.RegisterAuthRequest;
import by.novik.caloriecounter.entity.User;
import by.novik.caloriecounter.repository.RoleRepository;
import by.novik.caloriecounter.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public void save(RegisterAuthRequest request) {
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setWeight(request.getWeight());
        user.setHeight(request.getHeight());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setActivityLevel(request.getActivityLevel());
        user.setRole(roleRepository.findById(1L).orElseThrow());
        userRepository.save(user);
    }

    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public User getTokenForUserIfExists(AuthRequest authRequest) {
        return findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword()).orElseThrow();
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        User user = findByLogin(login).orElseThrow();
        if (passwordEncoder.matches(password, user.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public ProfileResponse showPersonalInformation(String login) {
        User user = findByLogin(login).orElseThrow();
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setLogin(login);
        profileResponse.setHeight(user.getHeight());
        profileResponse.setWeight(user.getWeight());
        profileResponse.setAge(user.getAge());
        profileResponse.setGender(user.getGender());
        profileResponse.setActivityLevel(user.getActivityLevel());
        return profileResponse;
    }

    public ProfileResponse editProfile(String login, int height, double weight, int age, String gender, String activityLevel) {
        User user = findByLogin(login).orElseThrow();
        user.setHeight(height);
        user.setWeight(weight);
        user.setAge(age);
        user.setGender(gender);
        user.setActivityLevel(activityLevel);
        userRepository.save(user);
        return showPersonalInformation(login);
    }
}
