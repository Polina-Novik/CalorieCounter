package by.novik.caloriecounter.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
public class ActivityResponse {
    private String name;

    private int caloriesSum;
}
