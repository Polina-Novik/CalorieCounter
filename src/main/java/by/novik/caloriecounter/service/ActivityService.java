package by.novik.caloriecounter.service;

import by.novik.caloriecounter.entity.Activity;
import by.novik.caloriecounter.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    public List<Activity> findAll() {
        return activityRepository.findAll();
    }

    public Activity findById(Long id) {
        return activityRepository.findById(id).orElseThrow();
    }

    public void add(String name, double calories) {
        Activity activity = new Activity();
        activity.setName(name);
        activity.setCaloriesBurnedPerMinute(calories);
        activityRepository.save(activity);
    }

    public void updateActivity(Long id, String name, double calories) {
        Activity activity = activityRepository.findById(id).orElseThrow();
        activity.setName(name);
        activity.setCaloriesBurnedPerMinute(calories);
        activityRepository.save(activity);
    }

    public void deleteById(Long id) {
        activityRepository.deleteById(id);
    }
}
