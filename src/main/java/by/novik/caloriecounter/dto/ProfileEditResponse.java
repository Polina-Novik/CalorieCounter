package by.novik.caloriecounter.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProfileEditResponse {

    @Min(value = 100, message = "Height should be greater than 100")
    @Max(value = 300, message = "Height should be less than 300")
    private int height;

    @Min(value = 10, message = "Weight should be greater than 10")
    @Max(value = 500, message = "Weight should be less than 500")
    private double weight;

    @Min(value = 14, message = "The application is available for persons over the age of 14")
    @Max(value = 150, message = "Age should be less than 150")
    private int age;

    @Pattern(regexp = "^(male|female)$", message = "Gender must be either 'male' or 'female'")
    private String gender;

    @Pattern(regexp = "sedentary|light|moderate|high|very_high",
            message = "Activity level must be sedentary, light, moderate, high or very_high")
    private String activityLevel;
}
