package by.novik.caloriecounter.repository;

import by.novik.caloriecounter.entity.DailyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyDataRepository extends JpaRepository<DailyData, Long> {
    Optional<DailyData> findByUserIdAndId(Long userId, Long dailyDataId);

    List<DailyData> findByUserId(Long id);

    Optional<DailyData> findById(Long id);

    Optional<DailyData> findByDateAndUserId(LocalDate date, Long userId);
}
