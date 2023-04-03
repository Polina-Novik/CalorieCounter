package by.novik.caloriecounter.dto;

import lombok.Data;

@Data
public class RegisterAuthRequest {
    private String login;

    private String password;

    private int height;

    private double weight;

    private int age;

    private String gender;

    private String activityLevel;
}
