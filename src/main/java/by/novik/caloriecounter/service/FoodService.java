package by.novik.caloriecounter.service;

import by.novik.caloriecounter.entity.Food;
import by.novik.caloriecounter.repository.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public Food findById(Long id) {
        return foodRepository.findById(id).orElseThrow();
    }

    public void add(String name, double calories) {
        Food food = new Food();
        food.setName(name);
        food.setCalories(calories);
        foodRepository.save(food);
    }

    public void update(Long id, String name, double calories) {
        Food food = foodRepository.findById(id).orElseThrow();
        food.setName(name);
        food.setCalories(calories);
        foodRepository.save(food);
    }

    public void deleteById(Long id) {
        foodRepository.deleteById(id);
    }
}
