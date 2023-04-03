package by.novik.caloriecounter.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class DailyDataResponse {

    private LocalDate date;

    private double consumedCalories;

    private List<String> foods = new ArrayList<>();

    private List<String> activities = new ArrayList<>();

    private double burnedCalories;

    private double weight;
}
