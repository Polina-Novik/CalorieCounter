package by.novik.caloriecounter.converter;

import by.novik.caloriecounter.dto.DailyDataResponse;
import by.novik.caloriecounter.entity.DailyData;
import org.springframework.stereotype.Component;

@Component
public class DailyDataConverter {
    public DailyDataResponse convert(DailyData dailyData) {
        DailyDataResponse dailyDataResponse = new DailyDataResponse();
        dailyDataResponse.setDate(dailyData.getDate());
        dailyDataResponse.setConsumedCalories(dailyData.getConsumedCalories());
        dailyDataResponse.setFoods(dailyData.getFoods());
        dailyDataResponse.setActivities(dailyData.getActivities());
        dailyDataResponse.setBurnedCalories(dailyData.getBurnedCalories());
        dailyDataResponse.setWeight(dailyData.getWeight());
        return dailyDataResponse;
    }

}
