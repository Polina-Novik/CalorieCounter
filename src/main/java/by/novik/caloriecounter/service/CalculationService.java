package by.novik.caloriecounter.service;

import by.novik.caloriecounter.entity.Activity;
import by.novik.caloriecounter.entity.DailyData;
import by.novik.caloriecounter.entity.Food;
import by.novik.caloriecounter.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CalculationService {

    private final UserService service;

    public double calculateBMR(String login) {
        User user = service.findByLogin(login).orElseThrow();
        double bmr;
        if (user.getGender().equals("male")) {
            bmr = 88.362 + (13.397 * user.getWeight()) + (4.799 * user.getHeight()) - (5.677 * user.getAge());
            return bmr;
        } else if (user.getGender().equals("female")) {
            bmr = 447.593 + (9.247 * user.getWeight()) + (3.098 * user.getHeight()) - (4.330 * user.getAge());
            return bmr;
        } else {
            throw new RuntimeException("gender is incorrect");
        }
    }

    public double calculateDailyCalorieNeeds(String login) {
        double bmr = calculateBMR(login);
        User user = service.findByLogin(login).orElseThrow();
        String activityLevel = user.getActivityLevel();
        double activityCoefficient = switch (activityLevel) {
            case "sedentary" -> 1.2;
            case "light" -> 1.375;
            case "moderate" -> 1.55;
            case "high" -> 1.725;
            case "very_high" -> 1.9;
            default -> 1;
        };
        return bmr * activityCoefficient;
    }

    public double calculateCaloriesFromFood(Food food, double grams) {
        double caloriesPerGram = food.getCalories() / 100.0;
        return caloriesPerGram * grams;
    }

    public double calculateCaloriesFromActivity(Activity activity, int minutes, DailyData dailyData) {

        double metValue = activity.getCaloriesBurnedPerMinute();
        double weightInKg = dailyData.getWeight();
        double trueCaloriesPerMinute = metValue * weightInKg * 3.5 / 200;
        return trueCaloriesPerMinute * minutes;
    }
}
