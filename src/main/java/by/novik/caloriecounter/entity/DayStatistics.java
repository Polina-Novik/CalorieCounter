package by.novik.caloriecounter.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="user_statistics")
public class UserStatistics {
    @Id
    private Long id;
    private String name;
    private String password;
    private int height;
    private int


}
