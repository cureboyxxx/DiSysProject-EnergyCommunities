package at.uastw.JavaFxGuiAPI.repository;

import at.uastw.JavaFxGuiAPI.entity.CurrentPercentageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CurrentPercentageRepository
        extends JpaRepository<CurrentPercentageEntity, LocalDateTime> {

    CurrentPercentageEntity findTopByOrderByHourDesc();
}