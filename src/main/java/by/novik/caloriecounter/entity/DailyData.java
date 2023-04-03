package by.novik.caloriecounter.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@ToString
@Data
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
}
