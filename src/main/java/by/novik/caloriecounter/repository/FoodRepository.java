package by.novik.caloriecounter.repository;

import by.novik.caloriecounter.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAll();
}
