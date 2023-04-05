package by.novik.caloriecounter.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@ToString
@Data
@RequiredArgsConstructor
@Table(name = "daily_data")
public class DailyData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private LocalDate date;

    private double consumedCalories;

    @ElementCollection
    @JoinColumn(name = "daily_data_food")
    private List<String> foods = new ArrayList<>();

    @ElementCollection
    @JoinColumn(name = "daily_data_activity")
    private List<String> activities = new ArrayList<>();

    private double burnedCalories;

    private double weight;

    public DailyData(User user, LocalDate date, double consumedCalories, List<String> foods,
                     List<String> activities, double burnedCalories, double weight) {
        this.user = user;
        this.date = date;
        this.consumedCalories = consumedCalories;
        this.foods = foods;
        this.activities = activities;
        this.burnedCalories = burnedCalories;
        this.weight = weight;
    }
}
