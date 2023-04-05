package by.novik.caloriecounter.converter;

import by.novik.caloriecounter.dto.ProfileResponse;
import by.novik.caloriecounter.dto.RegisterAuthRequest;
import by.novik.caloriecounter.entity.User;
import by.novik.caloriecounter.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User convert(RegisterAuthRequest request) {
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setWeight(request.getWeight());
        user.setHeight(request.getHeight());
        user.setAge(request.getAge());
        user.setGender(request.getGender());
        user.setActivityLevel(request.getActivityLevel());
        user.setRole(roleRepository.findById(1L).orElseThrow());
        return user;
    }

    public ProfileResponse convert(User user) {
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setLogin(user.getLogin());
        profileResponse.setHeight(user.getHeight());
        profileResponse.setWeight(user.getWeight());
        profileResponse.setAge(user.getAge());
        profileResponse.setGender(user.getGender());
        profileResponse.setActivityLevel(user.getActivityLevel());
        return profileResponse;
    }

}
