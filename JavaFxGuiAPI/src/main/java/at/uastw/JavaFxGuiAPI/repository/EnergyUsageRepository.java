package at.uastw.JavaFxGuiAPI.repository;

import at.uastw.JavaFxGuiAPI.entity.EnergyUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EnergyUsageRepository extends JpaRepository<EnergyUsageEntity, LocalDateTime> {

    List<EnergyUsageEntity> findByHourBetweenOrderByHourAsc(
            LocalDateTime start,
            LocalDateTime end
    );
}