package by.novik.caloriecounter.service;

import by.novik.caloriecounter.converter.DailyDataConverter;
import by.novik.caloriecounter.dto.DailyDataResponse;
import by.novik.caloriecounter.entity.Activity;
import by.novik.caloriecounter.entity.DailyData;
import by.novik.caloriecounter.entity.Food;
import by.novik.caloriecounter.entity.User;
import by.novik.caloriecounter.repository.DailyDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class DailyDataService {
    private final DailyDataRepository dailyDataRepository;
    private final UserService userService;
    private final CalculationService calculationService;
    private final FoodService foodService;
    private final ActivityService activityService;
    private final DailyDataConverter converter;


    public void deleteById(Long id) {
        dailyDataRepository.deleteById(id);
    }

    public List<DailyDataResponse> findByUserName(String login) {
        List<DailyData> dailyDataList = dailyDataRepository.findByUserId(userService
                .findByLogin(login).orElseThrow().getId());
        List<DailyDataResponse> dailyDataResponses = new ArrayList<>();
        for (DailyData dailyData : dailyDataList) {
            DailyDataResponse userResponse = converter.convert(dailyData);
            dailyDataResponses.add(userResponse);
        }
        return dailyDataResponses;
    }

    public DailyDataResponse createDailyData(String login, int year, int month, int day) {
        User user = userService.findByLogin(login).orElseThrow();
        try {
            LocalDate date = LocalDate.of(year, month, day);
            LocalDate currentDate = LocalDate.now();
            if (dailyDataRepository.findByDateAndUserId(date, user.getId()).isEmpty()
                    && date.compareTo(currentDate) <= 0) {
                DailyData dailyData = new DailyData(user, date, 0, null, null, 0, user.getWeight());
                dailyDataRepository.save(dailyData);
                return converter.convert(dailyData);
            } else {
                throw new RuntimeException("data already exists");
            }
        } catch (DateTimeException e) {
            throw new RuntimeException();
        }
    }

    public DailyData findByUserNameAndId(String login, Long dailyDateId) {
        return dailyDataRepository.findByUserIdAndId(userService.findByLogin(login).orElseThrow()
                .getId(), dailyDateId).orElseThrow();
    }

    public DailyDataResponse findResponseByUserNameAndId(String login, Long dailyDateId) {
        return converter.convert(dailyDataRepository.findByUserIdAndId(userService.findByLogin(login)
                .orElseThrow().getId(), dailyDateId).orElseThrow());
    }

    public DailyDataResponse addFoodToDailyData(String login, Long dailyDataId, Long foodId, double grams) {
        DailyData dailyData = findByUserNameAndId(login, dailyDataId);
        Food food = foodService.findById(foodId);
        double calories = calculationService.calculateCaloriesFromFood(food, grams);
        dailyData.getFoods().add(food.getName() + " " + calories + " calories");
        dailyData.setConsumedCalories(dailyData.getConsumedCalories() + calories);
        dailyDataRepository.save(dailyData);
        return converter.convert(dailyData);
    }

    public DailyDataResponse deleteFoodFromDailyData(String login, Long dailyDataId, int foodToRemove) {
        DailyData dailyData = findByUserNameAndId(login, dailyDataId);
        String foodInfo = dailyData.getFoods().get(foodToRemove);
        double calories = getCalories(foodInfo);
        dailyData.getFoods().remove(foodToRemove);
        dailyData.setConsumedCalories(dailyData.getConsumedCalories() - calories);
        dailyDataRepository.save(dailyData);
        return converter.convert(dailyData);
    }

    public DailyDataResponse addActivityToDailyData(String login, Long dailyDataId, Long activityId, int minutes) {
        DailyData dailyData = findByUserNameAndId(login, dailyDataId);
        Activity activity = activityService.findById(activityId);
        double calories = calculationService.calculateCaloriesFromActivity(activity, minutes, dailyData);
        dailyData.getActivities().add(activity.getName() + " " + calories + " calories");
        dailyData.setBurnedCalories(dailyData.getBurnedCalories() + calories);
        dailyDataRepository.save(dailyData);
        return converter.convert(dailyData);
    }

    public DailyDataResponse deleteActivityFromDailyData(String login, Long dailyDataId, int activityToRemove) {
        DailyData dailyData = findByUserNameAndId(login, dailyDataId);
        String activityInfo = dailyData.getActivities().get(activityToRemove);
        double calories = getCalories(activityInfo);
        dailyData.getActivities().remove(activityToRemove);
        dailyData.setBurnedCalories(dailyData.getBurnedCalories() - calories);
        dailyDataRepository.save(dailyData);
        return converter.convert(dailyData);
    }

    private static double getCalories(String str) {
        String[] words = str.split(" ");
        String caloriesStr = words[words.length - 2];
        return Double.parseDouble(caloriesStr);
    }

    public DailyDataResponse updateWeight(String login, Long dailyDataId, double weight) {
        DailyData dailyData = findByUserNameAndId(login, dailyDataId);
        userService.findByLogin(login).orElseThrow().setWeight(weight);
        dailyData.setWeight(weight);
        dailyDataRepository.save(dailyData);
        return converter.convert(dailyData);
    }
}
