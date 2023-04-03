package by.novik.caloriecounter.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProfileResponse {
    private String login;
    private int height;

    private double weight;

    private int age;

    @Pattern(regexp = "^(male|female)$", message = "Gender must be either 'male' or 'female'")
    private String gender;

    @Pattern(regexp = "sedentary|light|moderate|high|very_high",
            message = "Activity level must be sedentary, light, moderate, high or very_high")
    private String activityLevel;
}
